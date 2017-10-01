package pl_metodos.metodo_simplex;

/**
 * Created by k3v!n on 30/09/2017.
 */

public class simplex {
    private int filas, columnas;
    private float[][] tabla;
    private boolean sinSolucion = false;

    public static enum ERROR{
        NO_OPTIMIZADO,
        OPTIMIZADO,
        SIN_SOLUCION
    }

    public simplex(int nRes, int nVar){
        filas = nRes;
        columnas = nVar;
        tabla = new float[filas][];

        for(int i=0; i<filas; i++){
            tabla[i] = new float[columnas];
        }
    }

    public void llenarTabla(float[][] coeficientes){
        for(int i=0; i<tabla.length; i++){
            System.arraycopy(coeficientes[i], 0, this.tabla[i], 0, coeficientes[i].length);
        }
    }

    public ERROR procesar(){
        if(verificarOptimizacion()){
            return ERROR.OPTIMIZADO;
        }

        int columnaPivote = encontrarColumna();

        float[] r = calcularR(columnaPivote);
        if(sinSolucion == true){
            return ERROR.SIN_SOLUCION;
        }

        int filaPivote = encontrarValorMenor(r);

        gSiguienteTabla(filaPivote, columnaPivote);

        return ERROR.NO_OPTIMIZADO;
    }

    private void gSiguienteTabla(int filaPivote, int columnaPivote){
        float valorPivote = tabla[filaPivote][columnaPivote];
        float[] valoresPivoteFila = new float[columnas];
        float[] valoresPivoteColumna = new float[columnas];
        float[] nuevaFila = new float[columnas];

        System.arraycopy(tabla[filaPivote], 0, valoresPivoteFila, 0, columnas);

        for(int i=0; i<filas; i++){
            valoresPivoteColumna[i] = tabla[i][columnaPivote];
        }

        for(int i=0; i<columnas; i++){
            nuevaFila[i] = valoresPivoteFila[i] / valorPivote;
        }

        for(int i=0; i<filas; i++){
            if(i != filaPivote){
                for(int j=0; j<columnas;j++){
                    float c=valoresPivoteColumna[i];
                    tabla[i][j] = tabla[i][j] - (c*nuevaFila[j]);
                }
            }
        }

        System.arraycopy(nuevaFila, 0, tabla[filaPivote], 0, nuevaFila.length);
    }

    private float[] calcularR(int columna){
        float[] entradasPositivas = new float[filas];
        float[] res = new float[filas];
        int tContadorNegativos = 0;
        for(int i=0; i<filas; i++){
            if(tabla[i][columna] > 0){
                entradasPositivas[i] = tabla[i][columna];
            }else {
                entradasPositivas[i] = 0;
                tContadorNegativos++;
            }
        }

        if(tContadorNegativos == filas){
            this.sinSolucion = true;
        }else{
            for(int i=0; i<filas; i++){
                float val = entradasPositivas[i];
                if(val > 0){
                    res[i] = tabla[i][columnas - 1] / val;
                }
            }
        }

        return  res;
    }

    private int encontrarColumna(){
        float[] valores = new float[columnas];
        int ubicacion = 0;

        int pos, contador = 0;
        for(pos = 0; pos < columnas - 1; pos++){
            if(tabla[filas - 1][pos] < 0){
                contador++;
            }
        }

        if(contador > 1){
            for(int i = 0; i<columnas-1; i++){
                valores[i] = Math.abs(tabla[filas - 1][i]);
            }
            ubicacion = encontrarValorGrande(valores);
        }else {
            ubicacion = contador - 1;
        }
        return  ubicacion;
    }

    private  int encontrarValorGrande(float[] valores){
        float maximo = 0;
        int c, ubicacion = 0;
        maximo = valores[0];

        for(c = 0; c<valores.length;c++){
            if(Float.compare(valores[c], maximo) > 0){
                maximo = valores[c];
                ubicacion = c;
            }
        }
        return  ubicacion;
    }

    private int encontrarValorMenor(float[] valor){
        float minimo;
        int c, ubicacion = 0;
        minimo = valor[0];

        for(c = 1; c < valor.length; c++){
            if(valor[c] > 0){
                if(Float.compare(valor[c], minimo) < 0){
                    minimo = valor[c];
                    ubicacion = c;
                }
            }
        }
        return ubicacion;
    }

    public boolean verificarOptimizacion(){
        boolean esOptimo = false;
        int vContador = 0;

        for(int i=0; i< columnas-1; i++){
            float val = tabla[filas - 1][i];
            if(val >= 0){
                vContador++;
            }
        }

        if(vContador == columnas - 1){
            esOptimo = true;
        }
        return esOptimo;
    }

    public float[][] getTabla(){
        return  tabla;
    }

}
