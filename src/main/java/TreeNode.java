package main.java;


/**
 * Clase que crea los nodos del arbol
 * @author Ernesto Zawadzki Hernandez
 */
public class TreeNode {

    /**
     * Propiedad que representa el texto de un nodo
     */
    String value;
    /**
     * Propiedad que representa el hijo izquierdo
     */
    TreeNode left;
    /**
     * Propiedad que representa el hijo derecho
     */
    TreeNode right;

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