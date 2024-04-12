package aed;

public interface ColaDePrioridad {

/*
 * inserta un elemento
 */
public void insertar(int id, int votos);

/*
 * devuelve el valor con mayor o menor prioridad y lo elimina
 */
public int desencolar();

/*
 * devuelve la raiz de la cola
 */
public int raiz();

/*
 * devuelve el tamaño de la cola de prioridad
 */
public int tamaño();

/*
 * indica si la cola esta tiene o no elementos
 */
public boolean estaVacio();

}