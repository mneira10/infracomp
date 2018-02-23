public class Mensaje {

    private int contenido;
    private int respuesta;

    public Mensaje(int contenido) {
        this.contenido = contenido;
        this.respuesta = -1;
    }

    public void setRespuesta(int n){
        respuesta = n;
    }

    public int getContenido() {
        return contenido;
    }

    public int getRespuesta() {
        return respuesta;
    }
}
