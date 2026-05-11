package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    /**
     * Constructs an instance containing the given terms.
     */
    public TernarySearchTreeAutocomplete(Collection<? extends CharSequence> terms) {
        this();
        addAll(terms);
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        for (CharSequence term : terms) {
            if (term != null && term.length() > 0) {
                overallRoot = insert(overallRoot, term, 0);
            }
        }
    }

    private Node insert(Node node, CharSequence term, int index) {
        char currentChar = term.charAt(index);
        if (node == null) {
            node = new Node(currentChar);
        }
        if (currentChar < node.data) {
            node.left = insert(node.left, term, index);
        } else if (currentChar > node.data) {
            node.right = insert(node.right, term, index);
        } else if (index < term.length() - 1) {
            node.mid = insert(node.mid, term, index + 1); 
        } else {
            node.isTerm = true;
        }
        return node;
    }


    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> result = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return result;
        }
        Node prefixEndNode = getNode(overallRoot, prefix, 0);
        if (prefixEndNode == null) {
            return result;  
        }
        if (prefixEndNode.isTerm) {
            result.add(prefix);
        }
        collectAll(prefixEndNode.mid, prefix, result);
        return result;
    }

    private Node getNode(Node node, CharSequence prefix, int index) {
        if (node == null) {
            return null;
        }
        char currentChar = prefix.charAt(index);
        if (currentChar < node.data) {
            return getNode(node.left, prefix, index);
        } else if (currentChar > node.data) {
            return getNode(node.right, prefix, index);
        } else if (index < prefix.length() - 1) {
            return getNode(node.mid, prefix, index + 1);
        } else {
            return node;
        }
    }

    private void collectAll(Node node, CharSequence prefix, List<CharSequence> result) {
        if (node == null) {
            return;
        }
        collectAll(node.left, prefix, result);
        collectAll(node.right, prefix, result);
        CharSequence extendedPrefix = prefix.toString() + node.data;
        if (node.isTerm) {
            result.add(extendedPrefix);
        }
        collectAll(node.mid, extendedPrefix, result);
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}
