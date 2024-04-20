package grpcholamundostream.cliente;

import com.proto.saludo.Holamundo.SaludoRequest;
import com.proto.saludo.Holamundo.SaludoResponse;
import com.proto.saludo.SaludoServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Cliente {
    public static void main(String[] args) {
        
        String host = "localhost";
        int PUERTO = 9000;
    
        ManagedChannel ch = ManagedChannelBuilder.forAddress(host, PUERTO).usePlaintext().build();
    
        saludarUnario(ch);
        saludarStream(ch);
        obtenerArchivote(ch);

        System.out.println("Apagando");
    
        ch.shutdown();
    
    }

    public static void saludarUnario(ManagedChannel ch){
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        SaludoRequest peticion = SaludoRequest.newBuilder().setMessage("Tristan").build();
        SaludoResponse respuesta = stub.saludo(peticion);
        System.out.println("Respuesta RPC : " +respuesta.getResultado());
    }

    public static void saludarStream(ManagedChannel ch){
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        SaludoRequest peticion = SaludoRequest.newBuilder().setMessage("Tristan").build();
        stub.saludoStream(peticion).forEachRemaining(respuesta ->{
            System.out.println("Respuesta RPC : " +respuesta.getResultado());
        });;
       
    }

    public static void obtenerArchivote(ManagedChannel ch){
        SaludoServiceGrpc.SaludoServiceBlockingStub stub = SaludoServiceGrpc.newBlockingStub(ch);
        SaludoRequest peticion = SaludoRequest.newBuilder().setMessage("Tristan").build();
        stub.leerArchivo(peticion).forEachRemaining(respuesta ->{
            System.out.println("Respuesta RPC : " +respuesta.getArchivote());
        });;
       
    }
}
