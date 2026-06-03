// || INFO ||
//
// Po odpaleniu programu zalecam o powiększenie konsoli żeby widoczny był cały tekst i niczego nie przegapić.
// Od linijki 216 do 316 jest wstawiony easter egg, którego można przetestować gdy imię ucznia, którego chcemy dodać będzie takie same jake podane imie użytkownika
//
// || KONIEC INFO ||

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Wszystkie klasy:

class Grade {
    private int ocena;
    private String przedmiot;

    public Grade (int ocena, String przedmiot) {
        this.ocena = ocena;
        this.przedmiot = przedmiot;
    }

    public int getOcena() {
        return ocena;
    }

    public String getPrzedmiot() {
        return przedmiot;
    }

    private String colorGrade() {
        if (ocena <= 2) {
            return "\u001B[91m" + ocena + "\u001B[0m"; // czerwony
        } else if (ocena <= 4) {
            return "\u001B[33m" + ocena + "\u001B[0m"; // ciemno żółty
        } else if (ocena == 5) {
            return "\u001B[32m" + ocena + "\u001B[0m"; // zielony
        } else {
            return "\u001B[92m" + ocena + "\u001B[0m"; // jasnozielony
        }
    }

    @Override
    public String toString() {
        return przedmiot + ": " + colorGrade();
    }
}

class Student {
    private String imie;
    private List<Grade> oceny;

    public Student (String imie) {
        this.imie = imie;
        this.oceny = new ArrayList<>();
    }

    public String getImie() {
        return imie;
    }

    public void addGrade(Grade ocena) {
        oceny.add(ocena);
    }

    public String getAvg() {
        if (oceny.isEmpty()) {
            return "0.0";
        }

        int sum = 0;
        for (Grade g : oceny) {
            sum += g.getOcena();
        }

        double avg = (double) sum / oceny.size();

        if (avg < 1.75) {
            return "\033[31m" + avg + "\033[0m \u001B[91m(nieklasyfikowany)\u001B[0m";
        } else if (avg > 4.9) {
            return "\u001B[92m" + avg + "\u001B[0m \u001B[92m(doskonała średnia!)\u001B[0m";
        } else {
            return "\033[32m" + avg + "\033[0m";
        }
    }

    public void showGrades() {
        System.out.println("\nOceny ucznia: \033[36m" + imie + "\033[0m");
        for (Grade g : oceny) {
            System.out.println(g);
        }
    }
}

class Subject {
    private String imie;

    public Subject (String imie) {
        this.imie = imie;
    }

    public String getImie() {
        return imie;
    }
}

class Diary {
    private List<Student> uczniowie;

    public Diary() {
        uczniowie = new ArrayList<>();
    }

    public void addStudent(Student uczen) {
        uczniowie.add(uczen);
    }

    public Student findStudent(String name) {
        for (Student s : uczniowie) {
            if (s.getImie().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    public void showAllStudents() {
        if (uczniowie.isEmpty()) {
            System.out.println("\033[31mBrak uczniów w bazie!\033[0m");
            System.out.println("\033[38;5;160mAby wyświetlić uczniów musisz najpierw ich dodać do dziennika.\033[0m");
        } else {
            for (Student s : uczniowie) {
                System.out.println("Pełna lista uczniów zarejestrowanych do dziennika: \n");
                System.out.println("- " + "\033[36m" + s.getImie() + "\033[0m");
            }
        }
    }
}

// Główny program:

public class Main {
    public static void main(String[] args) {
        Diary diary = new Diary();
        Scanner scanner = new Scanner(System.in);
        String named = "";

//      z tą notką to miałem niezłą walkę :P
        String ntka = "\n ________________________\n|" + "\u001B[3m         NOTKA:         " + "\u001B[0m|\n"
                + "|------------------------|\n"
                + "|" + "\u001B[3m  login: admin          " + "\u001B[0m" + "|\n|" +
                "\u001B[3m  haslo: baseball       " + "\u001B[0m|\n ------------------------\n";

        System.out.print("\n\u001B[37mnotka z hasłem (tak/nie):\u001B[0m ");
        String yn = scanner.nextLine();

        if (yn.equalsIgnoreCase("tak")) {

            System.out.print(ntka);
        } else if (!yn.equalsIgnoreCase("nie")) {
            System.out.print("Co ty za głupoty piszesz misiu kolorowy?\n");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("\nWitaj w dzienniku elektronicznym `DziennikPL!` ");
        System.out.println("Zaloguj się jako admin lub spędź swój dzień poza naszymi usługami");

        for (int i = 3; i > 0; i--) {
            System.out.println("Liczba prób: " + i);
            System.out.print("login: "); String login = scanner.nextLine();
            System.out.print("hasło: "); String haslo = scanner.nextLine();

            if (login.equals("admin") && haslo.equals("baseball")) {
                System.out.print("\n");
                System.out.print("Access granted! Have a good one Mr...?: ");
                named = scanner.nextLine();
                named = (named.substring(0, 1)).toUpperCase() + named.substring(1);
                System.out.println("So have a nice time Mr. " + named + "!");
                break;
            } else {
                System.out.println("\n\u001B[91mNieprawidłowy login lub hasło.\u001B[0m\n");
            }
            if (i == 1) {
                System.out.print("wyjdź.\n");
                System.exit(0);
            }
        }


        while (true) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\nPanel zarządzania dziennikiem: ");
            System.out.println("1. Dodaj ucznia");
            System.out.println("2. Dodaj ocenę");
            System.out.println("3. Pokaż oceny ucznia");
            System.out.println("4. Pokaż średnią");
            System.out.println("5. Lista uczniów");
            System.out.println("0. Wyjście");

            System.out.print("\nOpcja nr: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\nWybrano funkcję dodawanie ucznia. ");
                    System.out.print("Imię ucznia: ");
                    String imie = scanner.nextLine();
                    imie = imie.substring(0, 1).toUpperCase() + imie.substring(1);

//                    taki mały easter egg do zabawy ;P
                    if (imie.equals(named)) {
                        System.out.println("\nMhm, okej, a powiedz mi jeszcze Panie " + named + ", jak nazywa się twój uczeń?");
                        System.out.print("nazwisko ucznia: ");
                        String studentSurname = scanner.nextLine();

                        String msg = "\n\u001B[37mA jak się Pan nazywa?: \u001B[0m";
                        for (char c : msg.toCharArray()) {
                            System.out.print(c);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        String adminSurname = scanner.nextLine();

                        if (studentSurname.equalsIgnoreCase(adminSurname)) {
                            String msg2 = "\n\u001B[38;2;139;0;0m   ...   \u001B[0m\n\n";
                            for (char c : msg2.toCharArray()) {
                                System.out.print(c);

                                try {
                                    if (c == '.') {
                                        Thread.sleep(800);
                                    } else if (c == ' ') {
                                        Thread.sleep(1);
                                    }
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            String txt1 = "\n\u001B[90mjesteś uczniem, czy mam rację?: \u001B[0m";
                            for (char c : txt1.toCharArray()) {
                                System.out.print(c);
                                System.out.flush();
                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            String yon = scanner.nextLine();

                            String txt2 = "\u001B[90modczytałeś hasło z kartki, prawda??: \u001B[0m";
                            for (char c : txt2.toCharArray()) {
                                System.out.print(c);
                                System.out.flush();
                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            String yon2 = scanner.nextLine();
                            System.out.println();
                            System.out.println();

                            String run = "\u001B[38;2;139;0;0mㅤㅤㅤㅤㅤㅤㅤWYNOŚ SIĘ ZTĄD\n\n\u001B[0m";
                            for (char c : run.toCharArray()) {
                                System.out.print(c);
                                System.out.flush();
                                try {
                                    if (c == 'ㅤ') {
                                        Thread.sleep(1);
                                    } else {
                                        Thread.sleep(20 + (int)(Math.random() * 120));
                                    }
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            System.exit(0);

                        } else {
                            System.out.print("\nTo świetnie, przepraszam za kłopot i życzę udanej reszty dnia!\nO! I oczywiście ");
                            diary.addStudent(new Student(imie));
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.print("\033[32mpomyślnie dodaję ucznia!\033[0m\n");
                            break;
                        }

                    } else {
                        diary.addStudent(new Student(imie));

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        System.out.print("\n\033[32mPomyślnie dodano ucznia!\033[0m\n");
                        break;
                    }

                case 2:
                    System.out.println("\nWybrano funkcję dodawanie oceny.");
                    System.out.print("Imię ucznia: ");
                    imie = scanner.nextLine();
                    Student s = diary.findStudent(imie);

                    if (s != null) {
                        System.out.print("Przedmiot: ");
                        String subject = scanner.nextLine();

                        System.out.print("Ocena: ");
                        int grade = scanner.nextInt();

                        if (grade > 6) {
                            System.out.print("\n\033[38;5;160mPokręciło Cię? Nie możesz wstawić oceny wyższej od 6.\033[0m\n");
                            break;
                        } else if (grade < 1) {
                            System.out.print("\n\033[38;5;160mA czy Ciebie czasem troszeczkę nie pokręciło? Nie możesz wstawić oceny niższej niż 1.\033[0m\n");
                            break;
                        }

                        s.addGrade(new Grade(grade, subject));

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        System.out.print("\n\033[32mPomyślnie dodano ocenę!\033[0m\n");
                    } else {
                        System.out.println("\n\033[31mNie znaleziono ucznia!\033[0m");
                        System.out.println("\033[38;5;160mAby dodać ocenę uczniowi musisz go najpierw dodać do dziennika.\033[0m");
                    }
                    break;

                case 3:
                    System.out.println("\nWybrano funkcję listy ocen uczniów.");
                    System.out.print("Imię ucznia: ");
                    imie = scanner.nextLine();
                    s = diary.findStudent(imie);

                    if (s != null) {
                        s.showGrades();
                    } else {
                        System.out.println("\n\033[31mNie znaleziono ucznia!\033[0m");
                        System.out.println("\033[38;5;160mAby zobaczyć ocenę ucznia musisz go najpierw dodać do dziennika.\033[0m");
                    }
                    break;

                case 4:
                    System.out.println("\nWybrano funkcję średniej ocen uczniów.");
                    System.out.print("Imię ucznia: ");
                    imie = scanner.nextLine();
                    s = diary.findStudent(imie);

                    if (s != null) {
                        System.out.println("Średnia ocen: " + s.getAvg());
                    } else {
                        System.out.println("\n\033[31mNie znaleziono ucznia!\033[0m");
                        System.out.println("\033[38;5;160mAby zobaczyć średnią ocen ucznia musisz go najpierw dodać do dziennika.\033[0m");
                    }
                    break;

                case 5:
                    System.out.println("\nWybrano funkcję listy uczniów. ");
                    diary.showAllStudents();
                    break;

                case 0:
                    System.out.print("\n\033[96mThank you for utilizing our secure digital environment.\n" +
                            "Your session has been successfully terminated to guarantee" +
                            "\nthe absolute confidentiality and integrity of your personal data.\n\033[0m");
                    System.exit(0);
            }
        }
    }
}


// 400!
