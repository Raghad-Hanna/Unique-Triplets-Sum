import java.util.*;

public class Main {
    // Storing every number and its original index (before sorting) regardless of whether the number is repeated or not
    private static IndexedValue[] numbers;
    private static int expectedSum;

    // Storing every unique number once with all its original indexes (considering the possible repetitions of the number)
    private static Map<Integer, List<Integer>> numbersToOriginalIndexes = new HashMap<>();

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
            int number = Integer.parseInt(input[i]);

            if(numbersToOriginalIndexes.containsKey(number)) {
                List<Integer> originalIndexes = numbersToOriginalIndexes.get(number);
                originalIndexes.add(i);
            }
            else {
                List<Integer> indexes = new ArrayList<>();
                indexes.add(i);
                numbersToOriginalIndexes.put(number, indexes);
            }

            numbers[i] = new IndexedValue(number, i);
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

                // If we know that the third number is smaller than the second number (given that the array is sorted)
                // then we know for sure the third number doesn't exist in the range of numbers which are bigger than it,
                // so we break out of the loop, as the second number is only going to get bigger and bigger with every
                // subsequent iteration
                if(neededThirdNumber < secondNumber.value())
                    break;

                boolean thirdNumberFound = binarySearch(j + 1, numbers.length - 1, neededThirdNumber);

                if(thirdNumberFound) {
                    List<Integer> originalIndexes = numbersToOriginalIndexes.get(neededThirdNumber);
                    for(int originalIndex: originalIndexes) {
                        List<Integer> uniqueTriplet = new ArrayList<>();
                        uniqueTriplet.add(firstNumber.index());
                        uniqueTriplet.add(secondNumber.index());
                        uniqueTriplet.add(originalIndex);
                        uniqueTriplets.add(uniqueTriplet);
                    }
                }
            }
        }
    }

    private static boolean binarySearch(int left, int right, int neededNumber) {
        while(left <= right) {
            int middle = (left + right) / 2;

            if(numbers[middle].value() == neededNumber)
                return true;
            else if(numbers[middle].value() > neededNumber)
                right = middle - 1;
            else
                left = middle + 1;
        }
        return false;
    }

    private static void outputUniqueTriplets() {
        uniqueTriplets.stream().forEach(System.out::println);
    }
}
