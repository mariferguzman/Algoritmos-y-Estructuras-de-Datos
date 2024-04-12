package aed;

public class SistemaCNE {
    private String[] nombresPartidos;
    private String[] nombresDistritos;
    private int[] diputadosDeDistritos;
    private int[][] rangoMesasDistritos;
    private int[] votosPresidenciales;
    private int[][] votosDiputados;
    private int[] mesasRegistradas;
    private int votosTotalesPresidenciales;    
    private int primerPartido;
    private int segundoPartido;
    private int[] votosTotalesDiputadosDistrito;
    private MaxHeap[] dHondtPoDistrito;
    private int[][] bancasDiputados;
    private boolean[] distritoYaRegistrado;
    

    public class VotosPartido{
        private int presidente;
        private int diputados;
        VotosPartido(int presidente, int diputados){this.presidente = presidente; this.diputados = diputados;}
        public int votosPresidente(){return presidente;}
        public int votosDiputados(){return diputados;}
    }



    public SistemaCNE(String[] nombresDistritos, int[] diputadosPorDistrito, String[] nombresPartidos, int[] ultimasMesasDistritos) {
        int p = nombresPartidos.length;                                     // O(1)                                                                    
        int d = nombresDistritos.length;                                    // O(1)
        this.nombresPartidos = nombresPartidos;                             // O(1)
        this.nombresDistritos = nombresDistritos;                           // O(1)
        this.diputadosDeDistritos = diputadosPorDistrito;                   // O(1)
        this.rangoMesasDistritos = new int[d][2];                           // O(D)
        this.votosPresidenciales = new int[p];                              // O(P)
        this.votosDiputados = new int [d][p];                               // O(D*P)
        this.mesasRegistradas = new int [ultimasMesasDistritos[d-1]-1];     // O(D)
        this.primerPartido = -1;                                            // O(1)
        this.segundoPartido = -2;                                           // O(1)
        this.votosTotalesPresidenciales = 0;                                // O(1)
        this.votosTotalesDiputadosDistrito= new int[d];                     // O(D)
        this.distritoYaRegistrado = new boolean[d];                         // O(D)
        this.bancasDiputados = new int[d][p];                               // O(D*P)
        this.dHondtPoDistrito = new MaxHeap[d];                             // O(D)



        for (int i = 0; i < d; i++){                                        // O(D)
            votosTotalesDiputadosDistrito[i] = 0;                           // O(1)
            if (i==0){
                rangoMesasDistritos[i][0] = 0;                               // O(1)
                rangoMesasDistritos[i][1] = ultimasMesasDistritos[0];       // O(1)
            }
            else {
                rangoMesasDistritos[i][0] = ultimasMesasDistritos[i-1];     // O(1)
                rangoMesasDistritos[i][1] = ultimasMesasDistritos[i];       // O(1)
            }
            for (int j=0; j<p; j++){                                        // O(P)
                votosDiputados[i][j] = 0;                                   // O(1)
                votosPresidenciales[j]=0;                                   // O(1)
            }
            }
        }

    // esto vale O(P*D), porque como P y D no son lo mismo, y al estar un for dentro de otro, esto pasa que en el peor caso seria un sumatoria dentro de otra,
    // es decir que quedaria P*D, con lo cual O(P*D), y luego tengo algunos constructores con valor O(D*P).



    public String nombrePartido(int idPartido) {
        return nombresPartidos[idPartido];                                  // O(1)
    }

    // esto vale O(1), ya que la unica linea de codigo que hay en la operacion vale O(1), ya que es un buscar en un array.

    public String nombreDistrito(int idDistrito) {
        return nombresDistritos[idDistrito];                                // O(1)
    }

    // esto vale O(1), ya que la unica linea de codigo que hay en la operacion vale O(1), ya que es un buscar en un array.

    public int diputadosEnDisputa(int idDistrito) {
        return diputadosDeDistritos[idDistrito];                            // O(1)
    }

    // esto vale O(1), ya que la unica linea de codigo que hay en la operacion vale O(1), ya que es un buscar en un array.


    public String distritoDeMesa(int idMesa) {
        int distrito = buscadorDistritoParaMesa(idMesa);                    // O(log(D))
        return nombresDistritos[distrito];                                  // O(1)
    }
    // esto vale O(log(D)), porque utilizamos la funcion auxiliar buscadorDistritoParaMesa, que realiza una busqueda binaria alrededor de los distritos, con lo cual esta funcion auxiliar vale O(log(D)),
    // y despues la siguiente linea vale O(1), entonces: O(1) + O(log(D) = O(max{1, log(D)}) = O(log(D))


    private int buscadorDistritoParaMesa(int idMesa){                       // O(log(D))
        int izq = 0;                                                                                        // O(1)
        int der = rangoMesasDistritos.length - 1;                                                           // O(1)
        if (idMesa < rangoMesasDistritos[0][0] || idMesa > rangoMesasDistritos[der][1] || izq > der){       // O(1)
            throw new IndexOutOfBoundsException();
        }

        while (izq <= der) {                                                                                // O(log(D))
            int medio = (izq + der) / 2;                                                                    // O(1)
            int[] rango = rangoMesasDistritos[medio];                                                       // O(1)

            if (idMesa >= rango[0] && idMesa < rango[1]){                                                   // O(1)
                return medio;                                                                               // O(1)
            } else if (idMesa < rango[0]){                                                                  // O(1)
                der = medio - 1;                                                                            // O(1)
            } else {    
                izq = medio + 1;                                                                            // O(1)
            }
        }
        return izq;                                                                                         // O(1)
    }
    //O(log(D)


    public void registrarMesa(int idMesa, VotosPartido[] actaMesa) {
        mesasRegistradas[idMesa] = idMesa;                                                  // O(1)
        int max_votosP = -1;                                                                // O(1)
        int max_votosS = -2;                                                                // O(1)
        int distrito = buscadorDistritoParaMesa(idMesa);                                    // O(log(D))

        for (int idPartido = 0; idPartido < actaMesa.length; idPartido++){                  // O(P)
            votosPresidenciales[idPartido] += actaMesa[idPartido].votosPresidente();        // O(1)
            votosDiputados[distrito][idPartido] += actaMesa[idPartido].votosDiputados();    // O(1)
            votosTotalesPresidenciales += actaMesa[idPartido].votosPresidente();            // O(1)
            votosTotalesDiputadosDistrito[distrito]+= actaMesa[idPartido].votosDiputados();

            if (max_votosP < votosPresidenciales[idPartido]) {                             
                max_votosS = max_votosP;                                                    // O(1)
                segundoPartido = primerPartido;                                             // O(1)   
                max_votosP = votosPresidenciales[idPartido];                                // O(1)
                primerPartido = idPartido;                                                  // O(1)
            }
            else if (max_votosS < votosPresidenciales[idPartido]) {                         // O(1)
                max_votosS = votosPresidenciales[idPartido];                                // O(1)
                segundoPartido = idPartido;                                                 // O(1)
            }            
        }
        int[] votosDiputadosPorDistrito = filtradoPorUmbral(votosDiputados[distrito], distrito);  // O(P)
        dHondtPoDistrito[distrito] = new MaxHeap(votosDiputadosPorDistrito);                    // O(P)

    }
    // esto vale O(P + log(D)), ya que la mayorias de las lineas de codigo valen O(1) y despues hay un bucle en un array donde su peor caso, es cuando haga todas las iteraciones, 
    // es decir O(P), luego utilizamos una funcion auxiliar que hace un busqueda binaria sobre los distritos ,es decir O(log(D)).Luego llamamos a la funcion auxiliar filtroPorUmbral que tiene un costo de O(P) y al crear el MaxHeap del ditrito nos cuesta O(P).
    //En total obviando todos los O(1), nos quedaria que O(P) + O(P) + O(P) + O(log(D)) = O(max{P + log(D)}) = O(P + Log(D))

    private int[] filtradoPorUmbral(int[] votosDiputados, int idDistrito){            // O(P)                                
        int[] filtrado = new int[votosDiputados.length-1];                          // O(1)
        for (int i=0; i< votosDiputados.length -1; i++){                            // O(P)
            if (partidoSuperaElUmbral(i,idDistrito)){                               // O(1)
                filtrado[i] = votosDiputados[i];                                    // O(1)
            }                                                                           
            else{
                filtrado[i] = 0;                                                    // O(1)
            }
        }
        return filtrado;                                                            // O(1)
    }

    private boolean partidoSuperaElUmbral(int partido, int distrito){               // O(1)
        boolean res = false;                                                        // O(1)
        int votosPartido = votosDiputados(partido, distrito);                       // O(1)
        int totalDeVotosDistrito = votosTotalesDiputadosDistrito[distrito];         // O(1)
        if ((((votosPartido * 100)/totalDeVotosDistrito)) >=3){                     // O(1)
            res = true;                                                             // O(1)
        }
        return res;                                                                 // O(1)
    }


    public int votosPresidenciales(int idPartido) {
        return votosPresidenciales[idPartido];                          // O(1)                     
    }
    // esto vale O(1), ya que la unica linea de codigo que hay en la operacion vale O(1), ya que es un buscar en un array.


    public int votosDiputados(int idPartido, int idDistrito) {
        return votosDiputados[idDistrito][idPartido];                   // O(1)
    }
    // esto vale O(1), ya que la unica linea de codigo que hay en la operacion vale O(1), ya que es un buscar en un array.


    public int[] resultadosDiputados(int idDistrito){                                                                                       // O(Dd * Log(P))
    if (!distritoYaRegistrado[idDistrito]){                                                                                                 // O(1)                                
        for(int bancas = 0; bancas < diputadosDeDistritos[idDistrito]; bancas++){                                                           // O(Dd)
            int masVotosConBancas = dHondtPoDistrito[idDistrito].desencolar();                                                                       // O(log(P))
            bancasDiputados[idDistrito][masVotosConBancas]++;                                                                                        // O(1)
            int votosDelPartidoEnDistrito = votosDiputados[idDistrito][masVotosConBancas];                                                           // O(1)     

            dHondtPoDistrito[idDistrito].insertar(masVotosConBancas, votosDelPartidoEnDistrito/(bancasDiputados[idDistrito][masVotosConBancas] + 1));         // O(Log(P))
        }
        distritoYaRegistrado[idDistrito] = true;                                                                                            // O(1)
    }
    return bancasDiputados[idDistrito];                                                                                                     // O(1)
    }
    // esto vale O(Dd * Log(P)), porque al haber un ciclo que cortas en el peor caso en la cantidad de bancas de un distrito d, este peor caso cuesta O(Dd) y luego dentro de ese ciclo desencolamos e insertmos elementos en un MaxHeap, por lo que los costos de esas operaciones son O(Log(P))
    // y como esta adentro del ciclo, el costo final del ciclo es O(Dd * Log(P)).al final juntando todas las lineas que cuestan O(1), tenemos que: O(1) + O(Dd * Log(P)) = O(Dd * Log(P))


    public boolean hayBallotage(){
        float porcentajePrimero;                                                                                                    // O(1)
        float porcentajeSegundo;                                                                                                    // O(1)
        porcentajePrimero = ((votosPresidenciales[primerPartido]*100) / votosTotalesPresidenciales);                                // O(1)
        porcentajeSegundo = ((votosPresidenciales[segundoPartido]*100) / votosTotalesPresidenciales);                               // O(1)
        return !((porcentajePrimero >= 45) || ((porcentajePrimero >= 40) && (porcentajePrimero - porcentajeSegundo >= 10) ));       // O(1)

    }
    // esto vale O(1), ya que: O(1) + O(1) + O(1) + O(1) + O(1) = O(max{1,1,1,1,1}) = O(1) 
}

/* 
InvRep(s´: SistemaCNE){

(s´.nombresPartidos.length == s´.votosPresidenciales.length) && (s´.nombresDistritos.length == s´.diputadosDeDistritos.length && s´.nombresDistritos.length == s´.dHondtPorDistrito.length && s´.nombresDistritos.length == s´.bancasDiputados.length) &&  (s´.votosDiputados.lenght == s´.nombresDistritos.lenght && forall i : int :: 0 <= i < s´.nombresDistritos ==>L s´.votosDiputados[i].length == s´.nombresPartidos.length)

(forall d: idDistrito :: 0 <= d < s´.nombresDistritos.length ==>L (s´.diputadosDeDistritos[d] > 0 && s´.rangoMesas[d][0] >= 0 && s´.rangoMesas[d][1] > 0 && s´.votosTotalesDiputadosDistritos[d] >= 0)) && (forall p : idPrtido:: 0 <= p < s´.nombresPartidos.length ==>L votospresidenciales[p] >= 0) && (s´.primerPartido in s´.nombresPartidos && s´.segundoPartido in s´.nombresPartidos) 
&& (forall d: idDistrito :: 0 <= d < s´.nombresDistritos.length ==>L 
        forall p : idPartido:: 0 <= p < s´.nombresPrtidos.length ==>L 
            s´.votosDiputados[d][p] >= 0)

((sum i: int :: 0 <= i < s´.nombresPartidos.length : s´.votosPresidenciales[i]) == s´.votosTotalesPresidenciales)
((forall d: idDistrito :: 0 <= d < s´.nombresDistritos ==>L
    (sum i : int :: 0<= i < s´.nombresPartidos.length : s´.votosDiputados[d][i]) == s´votosTotalesDiputadosDistrito[i])

(forall p: idPartido :: 0 <= p < s´.nombresPartido.lenght ==>L ((Exists i,j : idPartido :: 0 <= i,j < s´.nombresPartido.length)) && (i != j) && (s´.primerPartido = s´.nombresPartidos[i]) && (s´.segundoPartido == s´nombresPartido[j]) && (s´.nombresPartido[p]) < s´.nombresPartido[j] < s´.nombresPartido[i]))

((forall d: idDistrito :: 0 <= d < s´.nombresDistritos ==>L ((dHondtPorDistrito[i] debe ser un heap valido , ypara cada valor dentro de Heap este debe estar relacionado con s´.votosDiputados y con s´.bancasDiputados)

(forall d: idDistrito :: 0 <= d < s´.nombresDistritos.length ==>L (si s´.bancasYaRegistradas[d] es igual a true entonces s´.bancasDiputados[d] esta lista para devolver el valor deseado)

(forall i : int :: i in s´.mesasRegistradas ==>L s´.rangoMesas[0][0] <= i < s´.rangoMesas[s´.nombresDistrito.lenght][1])

}
*/
