public class LectorResumen {
    public int id;
    public String nombreCompleto;
    public int total;
    public int activos;
    public int devueltos;

    public LectorResumen(int id, String nombreCompleto) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.total = 0;
        this.activos = 0;
        this.devueltos = 0;
    }

    
}
