package grpcholamundostream.servidor;

import java.util.Scanner;

import com.proto.saludo.SaludoServiceGrpc;
import com.proto.saludo.Holamundo.Archivote;
import com.proto.saludo.Holamundo.SaludoRequest;
import com.proto.saludo.Holamundo.SaludoResponse;

import io.grpc.stub.StreamObserver;


public class ServidorImpl extends SaludoServiceGrpc.SaludoServiceImplBase {

    @Override
    public void saludo(SaludoRequest request, StreamObserver<SaludoResponse> responObserver){
        SaludoResponse respusta = SaludoResponse.newBuilder().setResultado("Hola " + request.getMessage()).build();
        responObserver.onNext(respusta);
        responObserver.onCompleted();
    }

    @Override
    public void saludoStream(SaludoRequest request, StreamObserver<SaludoResponse> responObserver){
        for (int i = 0; i < 10; i++) {
            SaludoResponse respuesta = SaludoResponse.newBuilder().setResultado("Hola "+request.getMessage() + " por " + i + " vez").build();
            responObserver.onNext(respuesta);
        }
        responObserver.onCompleted();
    }

    @Override
    public void leerArchivo(SaludoRequest saludoRequest, StreamObserver<Archivote> rObserver){
        try (Scanner scanner = new Scanner(ServidorImpl.class.getResourceAsStream("/archivote.csv"))){
            while (scanner.hasNextLine()){
                Archivote respuesta = Archivote.newBuilder().setArchivote(scanner.nextLine()).build();
                
                System.out.print(".");
                rObserver.onNext(respuesta);
            }
            rObserver.onCompleted();
        }
    }
}
