public class Servidor implements Runnable {

    Buffer bf;
    int id;

    public Servidor(Buffer bf,int id) {
        this.bf = bf;
        this.id = id;
    }

    @Override
    public void run() {
        while(!bf.acabado()){
            Mensaje temp = bf.removerMensaje();
            if(temp==null){
                synchronized (bf){
                    try {
                        System.out.println("Servidor: " + id+ " durmiendo");
                        bf.wait();
                        System.out.println("Servidor: " + id+ " despierto");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                procesar(temp);
                System.out.println("Servidor "+ id + " procesando mensaje...");
                synchronized (temp){
                    temp.notifyAll();
                }
            }
        }

        System.out.println("Servidor "+ id +" acabo");
    }

    public void procesar(Mensaje msg){
        msg.setRespuesta(msg.getContenido()-1);
    }
}
