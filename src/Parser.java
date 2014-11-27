/**
 * Парсит строковое математическое выражение и вычисляет его результат.
 */

public class Parser {

    public static int parse(String str) {
        // Удаляем пробелы и внешние скобки.
        str = removeLeadingBrackets(str.replace(" ", ""));
        // Проверяем, является ли строка числом.
        if (isDigit(str))
            return Integer.valueOf(str);
        // Определяем местоположение операторов "*" и "/".
        int operatorPosition = indexOfOperator(str, 1);
        // И если их не находим, ищем операторы "+" и "-".
        if (operatorPosition == -1)
            operatorPosition = indexOfOperator(str, 0);
        if (operatorPosition == -1)
            throw new RuntimeException("Operator not found");
        Character operator = str.charAt(operatorPosition);
        // Рекурсивно вызываем метод parse() для левой и правой части выражения,
        // и потом проводим с ними операцию operator с помощью метода calculate().
        return calculate(parse(str.substring(0, operatorPosition)), parse(str.substring(operatorPosition + 1)), operator);
    }

    /**
     * Метод удаляет внешние открывающую и закрывающую скобки, если строка
     * начинается и заканчивается этими символами.
     * @param str - входна строка.
     * @return - возвращает входную строку str с удаленными внешними скобками.
     */
    public static String removeLeadingBrackets(String str) {
        if(str.startsWith("(") && str.endsWith(")")) {
            // Итеративно считаем колечество открывающих и закрывающих скобок.
            int nBrackets = 0;
            for (int pos = 1; pos < str.length() - 1; pos ++) {
                Character c = str.charAt(pos);
                if (c == '(')
                    nBrackets++;
                if (c == ')')
                    nBrackets--;
                if (nBrackets < 0)
                    return str;
            }
            if (nBrackets == 0)
                // Рекурсивно снова вызываем метод, если есть вложеность скобок типа "(( ))".
                return removeLeadingBrackets(str.substring(1, str.length() - 1));
        }
        return str;
    }

    /**
     * Метод проверяет, состоит ли строка полностью из цифр.
     * @param str - входная строка.
     * @return - возвращает "true" - если строка полностью состоит из цифр. В ином случае - "false".
     */
    public static boolean isDigit(String str) {
        if (str == null && str.length() == 0)
            throw new RuntimeException("Operand not found");
        for (int pos = 0; pos < str.length(); pos++)
            if(!Character.isDigit(str.charAt(pos)))
                return false;
        return true;

    }

    /**
     * Метод возвращает индекс оператора, стоящего после "внешней" закрывающей скобки.
     * @param str - входная строка.
     * @param priority - приоритет оператора: 0 - для "*" и "/", 1 - для "+" и "-".
     * @return - возвращает индекс опертора; если метод его не находит - возвращает -1.
     */
    public static int indexOfOperator(String str, int priority) {
        int nBrackets = 0;
        for (int pos = 0; pos < str.length(); pos ++) {
            Character c = str.charAt(pos);
            if (c == '(')
                nBrackets++;
            if (c == ')')
                nBrackets--;
            if (priorityOperator(c) == priority && nBrackets == 0)
                return pos;
        }
        if (nBrackets != 0)
            throw new RuntimeException("In " + str + " number of opening and closing brackets mismatch");
        return -1;
    }

    /**
     * Метод расставляет приоритет исполнения для различных оперторов выражений.
     * @param c - символ оператора.
     * @return - возвращает величину приоритета опертора.
     */
    public static int priorityOperator(Character c) {
        switch (c) {
            case '+':
            case '-': return 0;
            case '*':
            case '/': return 1;
            default : return -1;
        }
    }

    /**
     * Метод производит операцию operator с правым и левым операндами выражения
     * @param leftOperand - левый операнд выражения.
     * @param rightOperand - правый операнд выражения.
     * @param operator - оператор выражения.
     * @return - возвращает результат операции.
     */
    public static int calculate(int leftOperand, int rightOperand, Character operator) {
        switch (operator) {
            case '+': return leftOperand + rightOperand;
            case '-': return leftOperand - rightOperand;
            case '*': return leftOperand * rightOperand;
            case '/': return leftOperand / rightOperand;
        }
        return 0;
    }





/*
//        if(str.startsWith("(") && str.endsWith(")") && str.) {
//            return parse(str.substring(1, str.length() - 1));
//        } else
        if (str.startsWith("(")) {
//            throw new RuntimeException("If expression starts with  '(' then it MUST ends with ')' " +
//                    "but expr = " + str);
            int count = 1, pos1 = 1, pos2 = 1;
            int right, left;
            do {
                right = str.indexOf(")", pos2);
                if (right != -1) {
                    count--;
                    pos2 = right + 1;
                }
                left = str.indexOf("(", pos1);
                if (left != -1) {
                    count++;
                    pos1 = left + 1;
                }

            } while (count > 0);
            return parse(str.substring(1, right));
        } else {
            int pos = 0;
            while (pos < str.length() - 1) {
                if (Character.isDigit(str.charAt(pos))) {
                    pos++;
                } else {
                    int leftOperand = Integer.valueOf(str.substring(0, pos));
                    char operator = str.charAt(pos);
                    int rightOperand = parse(str.substring(pos + 1));
                    switch (operator) {
                        case '+': return leftOperand + rightOperand;
                        case '-': return leftOperand - rightOperand;
                        case '*': return leftOperand * rightOperand;
                        case '/': return leftOperand / rightOperand;
                    }
                }
            }
        }
        return Integer.valueOf(str);
    }
    */
}
