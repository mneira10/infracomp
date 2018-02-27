import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Main {




    public static void main(String[] args) {
        int capacidadBf = 2;
        int nServidores;
        int nClientes;
        int[] peticiones;

        try(BufferedReader br = new BufferedReader(new FileReader("entrega1/src/config.txt"))){
            //lectura del archivo
            nClientes = Integer.parseInt(br.readLine().split(" ")[1].trim());
            nServidores = Integer.parseInt(br.readLine().split(" ")[1].trim());
            peticiones = new int[nClientes];
            for (int i = 0 ; i<nClientes ; i++){
                peticiones[i] = Integer.parseInt(br.readLine().split(" ")[1].trim());
            }
            Object lck = new Object();
            Buffer bf = new Buffer(capacidadBf,nClientes,lck);

            for(int i  = 0 ; i<nServidores;i++){
                new Thread(new Servidor(bf,i)).start();
            }
            long t = System.currentTimeMillis();

            System.out.println("Empezó");
            for(int i  = 0 ; i<nClientes;i++){
                new Thread(new Cliente(peticiones[i],i,bf)).start();
            }


            if(!bf.acabado()){
                synchronized (lck){
                    lck.wait();
                }
            }


            System.out.println("Main Terminó después de " + (System.currentTimeMillis()-t) + "ms.");



        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
