import java.util.Stack;
import java.util.Scanner;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

public class BinaryTreeTraversal {

    private static TreeNode buildTree(Scanner scanner, int value) {
        if (value == -1) {
            return null;
        }
        TreeNode node = new TreeNode(value);
        System.out.println("Enter left child value of " + value + " (or -1 to skip):");
        int leftValue = scanner.nextInt();
        System.out.println("Enter right child value of " + value + " (or -1 to skip):");
        int rightValue = scanner.nextInt();
        node.left = buildTree(scanner, leftValue);
        node.right = buildTree(scanner, rightValue);
        return node;
    }

    // Recursive Preorder Traversal
    private static String preorderRecursive(TreeNode root) {
        StringBuilder result = new StringBuilder();
        preorderRecursiveHelper(root, result);
        return result.toString().trim();
    }
    private static void preorderRecursiveHelper(TreeNode node, StringBuilder result) {
        if (node != null) {
            result.append(node.val).append(" ");    //visit the node
            preorderRecursiveHelper(node.left, result);
            preorderRecursiveHelper(node.right, result);
        }
    }

    // Recursive Inorder Traversal
    private static String inorderRecursive(TreeNode root) {
        StringBuilder result = new StringBuilder();
        inorderRecursiveHelper(root, result);
        return result.toString().trim();
    }
    private static void inorderRecursiveHelper(TreeNode node, StringBuilder result) {
        if (node != null) {
            inorderRecursiveHelper(node.left, result);
            result.append(node.val).append(" ");
            inorderRecursiveHelper(node.right, result);
        }
    }

    // Recursive Postorder Traversal
    private static String postorderRecursive(TreeNode root) {
        StringBuilder result = new StringBuilder();
        postorderRecursiveHelper(root, result);
        return result.toString().trim();
    }
    private static void postorderRecursiveHelper(TreeNode node, StringBuilder result) {
        if (node != null) {
            postorderRecursiveHelper(node.left, result);
            postorderRecursiveHelper(node.right, result);
            result.append(node.val).append(" ");
        }
    }

    // Iterative Preorder Traversal
    private static String preorderIterative(TreeNode root) {
        StringBuilder result = new StringBuilder();
        if (root == null) {
            return result.toString().trim();
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.append(node.val).append(" ");
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return result.toString().trim();
    }

    // Iterative Inorder Traversal
    private static String inorderIterative(TreeNode root) {
        StringBuilder result = new StringBuilder();
        if (root == null) {
            return result.toString().trim();
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            result.append(current.val).append(" ");
            current = current.right;
        }
        return result.toString().trim();
    }

    // Iterative Postorder Traversal
    private static String postorderIterative(TreeNode root) {
        StringBuilder result = new StringBuilder();
        if (root == null) {
            return result.toString().trim();
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        Stack<Integer> output = new Stack<>();    //to store the values of nodes in reverse order
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            output.push(node.val);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        while (!output.isEmpty()) {
            result.append(output.pop()).append(" ");
        }
        return result.toString().trim();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while(true) {
        System.out.println("Choose one of the following:\n1) Recursively.\n2) Iteratively.\n3)Exit.");
        int option = input.nextInt();

        int rootValue;
        TreeNode root;
            switch (option) {
                case 1:
                    System.out.println("Enter the value of the root node:");
                    rootValue = input.nextInt();
                    root = buildTree(input, rootValue);

                    System.out.println("Preorder Traversal: " + preorderRecursive(root));
                    System.out.println("Inorder Traversal: " + inorderRecursive(root));
                    System.out.println("Postorder Traversal: " + postorderRecursive(root));
                    System.out.println("---------------------------------");
                    break;
                case 2:
                    System.out.println("Enter the value of the root node:");
                    rootValue = input.nextInt();
                    root = buildTree(input, rootValue);

                    System.out.println("Preorder Traversal: " + preorderIterative(root));
                    System.out.println("Inorder Traversal: " + inorderIterative(root));
                    System.out.println("Postorder Traversal: " + postorderIterative(root));
                    System.out.println("---------------------------------");
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                default:
                    System.out.println("Choose a valid Option !!");
            }
        }
    }
}
