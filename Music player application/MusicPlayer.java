import java.io.*;
import java.util.Scanner;

class Node {
    String song;
    Node next;
    Node prev;

    public Node(String song) {
        this.song = song;
        this.next = null;
        this.prev = null;
    }
}

public class MusicPlayer {
    static Node top;

    static void toFile(String song) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("playlist.txt", true));
            writer.write(song);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void push(String song) {
        Node newNode = new Node(song);
        if (top == null) {
            top = newNode;
        } else if (!top.song.equals(song)) {
            newNode.next = top;
            top.prev = newNode;
            top = newNode;
        }
    }

    static void display() {
        Node temp = top;
        if (temp == null) {
            System.out.println("NO recently played tracks.");
            return;
        }
        System.out.println("#Recently played tracks-");
        while (temp != null) {
            System.out.println(temp.song);
            temp = temp.next;
        }
    }

    static void play(Node first) {
        Scanner scanner = new Scanner(System.in);
        printList(first);
        System.out.println("Choose song you wish to play: ");
        String song = scanner.nextLine();
        int flag = 0;

        while (first != null) {
            if (first.song.equals(song)) {
                System.out.println("Now Playing......" + song);
                flag++;
                push(song);
                break;
            } else {
                first = first.next;
            }
        }
        if (flag == 0) {
            System.out.println("Song not found.");
        }
    }

    static void printList(Node first) {
        System.out.println("Playlist Name- ");
        while (first != null) {
            System.out.println(first.song);
            first = first.next;
        }
    }

    static void addNode(Node first) {
        while (first.next != null) {
            first = first.next;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Song name: ");
        String song = scanner.nextLine();
        first.next = new Node(song);
        toFile(song);
    }

    static int countNodes(Node first) {
        int count = 0;
        while (first != null) {
            count++;
            first = first.next;
        }
        return count;
    }

    static void searchSong(Node first) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter song name to search: ");
        String song = scanner.nextLine();
        boolean found = false;
        while (first != null) {
            if (first.song.equals(song)) {
                System.out.println("Song found!");
                found = true;
                break;
            }
            first = first.next;
        }
        if (!found) {
            System.out.println("Song not found.");
        }
    }

    static void deleteSong(Node first) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter song name to delete: ");
        String song = scanner.nextLine();
        Node prev = null;
        Node current = first;
        boolean found = false;
        while (current != null) {
            if (current.song.equals(song)) {
                found = true;
                if (prev == null) {
                    top = current.next;
                } else {
                    prev.next = current.next;
                }
                System.out.println("Song deleted.");
                break;
            }
            prev = current;
            current = current.next;
        }
        if (!found) {
            System.out.println("Song not found.");
        }
    }

    static void displayLastPlayed() {
        if (top == null) {
            System.out.println("No last played tracks.");
        } else {
            System.out.println("Last Played Song - " + top.song);
        }
    }

    public static void main(String[] args) {
        int choice;
        Node start, hold;
        Scanner scanner = new Scanner(System.in);

        start = new Node("Playlist"); // Initial playlist name
        hold = start;
        top = null;

        do {
            System.out.println("\n1.Add New Song\n2.Delete Song\n3.Display Entered Playlist\n" +
                    "4.Total Songs\n5.Search Song\n6.Play Song\n7.Recently Played List\n" +
                    "8.Last Played\n9.Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNode(start);
                    break;
                case 2:
                    deleteSong(start.next); // start.next to skip the playlist name node
                    break;
                case 3:
                    printList(start);
                    break;
                case 4:
                    System.out.println("Total Songs: " + countNodes(start.next));
                    break;
                case 5:
                    searchSong(start.next);
                    break;
                case 6:
                    play(start.next);
                    break;
                case 7:
                    display();
                    break;
                case 8:
                    displayLastPlayed();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 9);
    }
}
