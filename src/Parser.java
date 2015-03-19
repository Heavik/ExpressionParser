/**
 * Парсит строковое математическое выражение и вычисляет его результат.
 */

public class Parser {

    public int parse(String str) {
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
                else if (c == ')')
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
            else if (c == ')')
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
}
