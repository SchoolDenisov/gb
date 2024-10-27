import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Scanner;

// Базовый класс Животное
abstract class Animal {
    private static int animalCount = 0;
    protected String name;
    protected String type;
    protected Date birthDate;
    protected List<String> commands = new ArrayList<>();

    public Animal(String name, String type, Date birthDate) {
        this.name = name;
        this.type = type;
        this.birthDate = birthDate;
        animalCount++;
    }

    public String getName() { return name; }
    public Date getBirthDate() { return birthDate; }
    public List<String> getCommands() { return commands; }

    public void addCommand(String command) {
        commands.add(command);
    }

    public static int getAnimalCount() {
        return animalCount;
    }
}

// Классы для домашних животных
class Dog extends Animal {
    public Dog(String name, Date birthDate) {
        super(name, "Dog", birthDate);
    }
}

class Cat extends Animal {
    public Cat(String name, Date birthDate) {
        super(name, "Cat", birthDate);
    }
}

class Hamster extends Animal {
    public Hamster(String name, Date birthDate) {
        super(name, "Hamster", birthDate);
    }
}

// Классы для вьючных животных
class Horse extends Animal {
    public Horse(String name, Date birthDate) {
        super(name, "Horse", birthDate);
    }
}

class Camel extends Animal {
    public Camel(String name, Date birthDate) {
        super(name, "Camel", birthDate);
    }
}

class Donkey extends Animal {
    public Donkey(String name, Date birthDate) {
        super(name, "Donkey", birthDate);
    }
}

// Основной класс для работы с реестром животных
public class AnimalRegistry {
    private List<Animal> animals = new ArrayList<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void addAnimal(Animal animal) {
        animals.add(animal);
        System.out.println("Добавлено новое животное: " + animal.getName());
    }

    public void listAnimalCommands(String name) {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(name)) {
                System.out.println("Команды для " + name + ": " + animal.getCommands());
                return;
            }
        }
        System.out.println("Животное с именем " + name + " не найдено.");
    }

    public void teachCommand(String name, String command) {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(name)) {
                animal.addCommand(command);
                System.out.println("Команда \"" + command + "\" добавлена для " + name);
                return;
            }
        }
        System.out.println("Животное с именем " + name + " не найдено.");
    }

    public void listAnimalsByBirthDate() {
        animals.stream()
                .sorted((a1, a2) -> a1.getBirthDate().compareTo(a2.getBirthDate()))
                .forEach(animal -> System.out.println(animal.getName() + " - " + dateFormat.format(animal.getBirthDate())));
    }

    public static void main(String[] args) {
        AnimalRegistry registry = new AnimalRegistry();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить животное");
            System.out.println("2. Показать команды животного");
            System.out.println("3. Обучить животное новой команде");
            System.out.println("4. Показать животных по дате рождения");
            System.out.println("5. Показать общее количество животных");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Чтение новой строки

            switch (choice) {
                case 1:
                    System.out.print("Введите имя животного: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите тип животного (Dog, Cat, Hamster, Horse, Camel, Donkey): ");
                    String type = scanner.nextLine();
                    System.out.print("Введите дату рождения (yyyy-MM-dd): ");
                    Date birthDate;
                    try {
                        birthDate = dateFormat.parse(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Неверный формат даты.");
                        continue;
                    }

                    switch (type.toLowerCase()) {
                        case "dog": registry.addAnimal(new Dog(name, birthDate)); break;
                        case "cat": registry.addAnimal(new Cat(name, birthDate)); break;
                        case "hamster": registry.addAnimal(new Hamster(name, birthDate)); break;
                        case "horse": registry.addAnimal(new Horse(name, birthDate)); break;
                        case "camel": registry.addAnimal(new Camel(name, birthDate)); break;
                        case "donkey": registry.addAnimal(new Donkey(name, birthDate)); break;
                        default: System.out.println("Неверный тип животного."); break;
                    }
                    break;

                case 2:
                    System.out.print("Введите имя животного: ");
                    registry.listAnimalCommands(scanner.nextLine());
                    break;

                case 3:
                    System.out.print("Введите имя животного: ");
                    String animalName = scanner.nextLine();
                    System.out.print("Введите новую команду: ");
                    registry.teachCommand(animalName, scanner.nextLine());
                    break;

                case 4:
                    registry.listAnimalsByBirthDate();
                    break;

                case 5:
                    System.out.println("Общее количество животных: " + Animal.getAnimalCount());
                    break;

                case 0:
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Неверный выбор. Повторите попытку.");
                    break;
            }
        }
    }
}
