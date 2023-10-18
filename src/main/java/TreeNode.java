package main.java;


/**
 * Clase que crea los nodos del arbol
 * @author Ernesto Zawadzki Hernandez
 */
public class TreeNode {

    /**
     * Propiedad que representa el texto de un nodo
     */
    public String value;
    /**
     * Propiedad que representa el hijo izquierdo
     */
    public TreeNode left;
    /**
     * Propiedad que representa el hijo derecho
     */
    public TreeNode right;

    /**
     * Constructor de la clase
     * @param value texto de un nodo
     */
    public TreeNode(String value) {

        this.value = value;
        this.left = null;
        this.right = null;

    }
}