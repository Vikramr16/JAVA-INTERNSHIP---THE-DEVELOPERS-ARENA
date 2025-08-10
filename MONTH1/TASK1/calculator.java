public class Calculator {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Calculator <number1> <operator> <number2>");
            return;
        }

        float value1 = Float.parseFloat(args[0]);
        char operation = args[1].charAt(0);
        float value2 = Float.parseFloat(args[2]);
        float output;

        switch (operation) {
            case '+':
                output = value1 + value2;
                System.out.println("The result of addition is: " + output);
                break;
            case '-':
                output = value1 - value2;
                System.out.println("The result of subtraction is: " + output);
                break;
            case '*':
                output = value1 * value2;
                System.out.println("The result of multiplication is: " + output);
                break;
            case '/':
                if (value2 != 0) {
                    output = value1 / value2;
                    System.out.println("The result of division is: " + output);
                } else {
                    System.out.println("Math Error: Division by zero is undefined.");
                }
                break;
            default:
                System.out.println("Invalid operator. Use one of +, -, *, /.");
        }
    }
}
