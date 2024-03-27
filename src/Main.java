import java.util.*;

public class Main {
    private static IndexedValue[] numbers;
    private static int expectedSum;

    private static List<List<Integer>> uniqueTriplets = new ArrayList<>();

    public static void main(String[] args) {
        inputNumbers();
        inputExpectedSum();

        Arrays.sort(numbers);

        findUniqueTriplets();

        outputUniqueTriplets();
    }

    private static void inputNumbers() {
        Scanner keyboard = new Scanner(System.in);
        String line = keyboard.nextLine();
        String[] input = line.split(" ");

        numbers = new IndexedValue[input.length];
        for(int i = 0; i < numbers.length; i++) {
            numbers[i] = new IndexedValue(Integer.parseInt(input[i]), i);
        }
    }

    private static void inputExpectedSum() {
        Scanner keyboard = new Scanner(System.in);
        expectedSum = keyboard.nextInt();
    }

    private static void findUniqueTriplets() {
        for(int i = 0; i < numbers.length - 2; i++) {
            IndexedValue firstNumber = numbers[i];

            for(int j = i + 1; j < numbers.length; j++) {
                IndexedValue secondNumber = numbers[j];

                int neededThirdNumber = expectedSum - firstNumber.value() - secondNumber.value();
                if(neededThirdNumber < secondNumber.value())
                    break;

                int foundThirdNumberIndex = binarySearch(numbers, j + 1, numbers.length - 1, neededThirdNumber);

                if(foundThirdNumberIndex != -1) {
                    int currentOccurrence = foundThirdNumberIndex;
                    List<Integer> uniqueTriplet = new ArrayList<>();
                    uniqueTriplet.add(firstNumber.index());
                    uniqueTriplet.add(secondNumber.index());
                    uniqueTriplet.add(currentOccurrence);
                    uniqueTriplets.add(uniqueTriplet);
                }
            }
        }
    }

    private static int binarySearch(IndexedValue[] numbers, int left, int right, int neededNumber) {
        while(left <= right) {
            int middle = (left + right) / 2;

            if(numbers[middle].value() == neededNumber)
                return numbers[middle].index();
            else if(numbers[middle].value() > neededNumber)
                right = middle - 1;
            else
                left = middle + 1;
        }
        return -1;
    }

    private static void outputUniqueTriplets() {
        uniqueTriplets.stream().forEach(System.out::println);
    }
}
