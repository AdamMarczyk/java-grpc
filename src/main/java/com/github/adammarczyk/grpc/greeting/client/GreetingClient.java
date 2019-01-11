package com.github.adammarczyk.grpc.greeting.client;

import com.proto.greet.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {

    public static void main(String[] args) {
        System.out.println("Hello, I'm a gRPC client");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        System.out.println("Creating stub");
        // create a greet service client (blocking - synchronous)
        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        // Unary
        // create a protocol buffer greeting message
//        Greeting greeting = Greeting.newBuilder()
//                .setFirstName("Adam")
//                .setLastName("Marczyk")
//                .build();
//
//        // create a protocol buffer GreetRequest
//        GreetRequest greetRequest = GreetRequest.newBuilder()
//                .setGreeting(greeting)
//                .build();
//
//        // call the RPC and get back a GreetResponse (protocol buffers)
//        GreetResponse greetResponse = greetClient.greet(greetRequest);
//
//        System.out.println(greetResponse.getResult());
//
//        System.out.println("Shutting down channel");

        // Server streaming
        GreetManyTimesRequest greetManyTimesRequest = GreetManyTimesRequest.newBuilder().setGreeting(Greeting.newBuilder().setFirstName("Adam")).build();

        // stream the response (in a blocking manner)
        greetClient.greetManyTimes(greetManyTimesRequest)
                .forEachRemaining(greetManyTimesResponse -> {
                    System.out.println(greetManyTimesResponse.getResult());
                });

        channel.shutdownNow();
    }
}
