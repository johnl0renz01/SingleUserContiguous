import java.io.IOException;
import java.lang.Thread;
import java.util.*;

public class Main {

	public static final int jobsQuantity = 6;
	public static float jobs[] = new float[jobsQuantity];
	public static float jobsConsidered[] = new float[jobsQuantity];

	public static void header() throws IOException, InterruptedException {
		new ProcessBuilder("clear").inheritIO().start().waitFor();
		System.out.println("PL-M3: ACT5: Single-User Contiguous Scheme");
		System.out.println("DELA CRUZ, JOHN LORENZ N.\n");
	}

	public static void pressEnterToContinue() {
		System.out.print("\nPress \'Enter\' key to continue...");
		try {
			Scanner scanner = new Scanner(System.in);
			scanner.nextLine();
		} catch (Exception e) {
		}
	}

	public static void jobsError(float arr[], int counter) {
		if (counter > 0) {
			for (int i = 0; i < counter; i++) {
				System.out.println("Input Job (" + (i + 1) + "/" + jobsQuantity + "): " + jobs[i]);
			}
		}
		if (counter == (jobsQuantity - 1) && jobs[jobsQuantity - 1] != 0) {
			System.out.println("Input Job (" + (counter + 1) + "/" + jobsQuantity + "): " + jobs[counter]);
		} else {
			System.out.print("Input Job (" + (counter + 1) + "/" + jobsQuantity + "): ");
		}
	}

	public static void jobListError() {
		System.out.println("\nJob List:");
		for (int i = 0; i < jobsQuantity; i++) {
			System.out.println("Job " + (i + 1) + " : " + jobs[i] + "K");
		}
	}

	public static void restartInput() {
		System.out.println("\nPress \'Enter\' key to continue...");
		System.out.println("\nAllocate or Deallocate?");
		System.out.print("Allocate(1) Deallocate(2): ");
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		header();

		int counter = 0;
		float memory = 0;
		float availableMemory = 0;
		float size = 0;
		boolean validMemory = false;
		boolean validSize = false;

		System.out.print("Enter memory capacity: ");

		do {
			try {
				do {
					Scanner input = new Scanner(System.in);
					memory = input.nextFloat();
					if (memory <= 0) {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.print("Enter memory capacity: ");
					}
				} while (memory <= 0);
				validMemory = true;
			} catch (InputMismatchException ex) {
				validMemory = false;
				System.out.println("Invalid input. Please enter a valid number.");
				Thread.sleep(1250);
				header();
				System.out.print("Enter memory capacity: ");
			}
		} while (!validMemory);

		System.out.print("Enter OS Size(K): ");

		do {
			try {
				do {
					Scanner input = new Scanner(System.in);
					size = input.nextFloat();
					if (size <= 0) {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.println("Enter memory capacity: " + memory);
						System.out.print("Enter OS Size(K): ");
					} else if (size >= memory) {
						System.out.println("Invalid input. The OS size should be less than memory size");
						Thread.sleep(1250);
						header();
						System.out.println("Enter memory capacity: " + memory);
						System.out.print("Enter OS Size(K: ");
					} else {
						validSize = true;
					}
				} while (memory <= 0);
			} catch (InputMismatchException ex) {
				validSize = false;
				System.out.println("Invalid input. Please enter a valid number.");
				Thread.sleep(1250);
				header();
				System.out.println("Enter memory capacity: " + memory);
				System.out.print("Enter OS Size(K): ");
			}
		} while (!validSize);

		availableMemory = memory;
		availableMemory -= size;
    
		do {
			float number = 0;
			boolean flag = false;
			System.out.print("Input Job (" + (counter + 1) + "/" + jobsQuantity + "): ");
			do {
				try {
					Scanner input = new Scanner(System.in);
					number = input.nextFloat();
					if (number > 0) {
						flag = true;
					} else {
						System.out.println("Invalid input. Please enter a positive number.");
						Thread.sleep(1250);
						header();
						System.out.println("Enter memory capacity: " + memory);
						System.out.println("Enter OS Size(K): " + size);
						jobsError(jobs, counter);
					}
				} catch (InputMismatchException ex) {
					flag = false;
					System.out.println("Invalid input. Please enter a valid number.");
					Thread.sleep(1250);
					header();
					System.out.println("Enter memory capacity: " + memory);
					System.out.println("Enter OS Size(K): " + size);
					jobsError(jobs, counter);
				}
			} while (!flag);

			jobs[counter] = number;
			counter++;
		} while (counter < jobsQuantity);

		System.out.println("\nJob List:");
		for (int i = 0; i < jobsQuantity; i++) {
			System.out.println("Job " + (i + 1) + " : " + jobs[i] + "K");
		}

		counter = (jobsQuantity - 1);
		pressEnterToContinue();
		header();
		System.out.println("Enter memory capacity: " + memory);
		System.out.println("Enter OS Size(K): " + size);
		jobsError(jobs, counter);
		jobListError();

		int anotherCounter = 0;

		do {
      float previousMemory = 0;
      boolean deallocated = false;
			int action = 0;
			boolean loaded = false;
			System.out.println("\nAllocating Job " + (anotherCounter + 1) + "...");
			Thread.sleep(1250);
			if (jobs[anotherCounter] <= availableMemory) {
				jobsConsidered[anotherCounter] = 1;
				System.out.println("Job " + (anotherCounter + 1) + " Memory allocated successfully.");
				availableMemory -= jobs[anotherCounter];
				loaded = true;
			} else {
				System.out.println("Job " + (anotherCounter + 1)
						+ " Memory failed to load. Insufficient memory space for allocation.");
			}

			System.out.println("OS(" + size + "K) Job " + (anotherCounter + 1) + "(" + jobs[anotherCounter]
					+ "K) Unused Space(" + availableMemory + "K)");

      if (anotherCounter < (jobsQuantity - 1)){
        pressEnterToContinue();
        boolean validChoice = false;
        System.out.println("\nAllocate or Deallocate?");
        System.out.print("Allocate(1) Deallocate(2): ");
        do {
          try {
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            if (choice == 1 || choice == 2) {
              if (choice == 1) {
                if (loaded) {
                  System.out.println("Allocation failed. Partition is not empty.");
                  Thread.sleep(1250);
                  header();
                  System.out.println("Enter memory capacity: " + memory);
                  System.out.println("Enter OS Size(K): " + size);
                  jobsError(jobs, counter);
                  jobListError();
                  System.out.println("\nAllocating Job " + (anotherCounter + 1) + "...");
                  System.out.println("Job " + (anotherCounter + 1) + " Memory allocated successfully.");
                  System.out.println("OS(" + size + "K) Job " + (anotherCounter + 1) + "("
                      + jobs[anotherCounter] + "K) Unused Space(" + availableMemory + "K)");
                  restartInput();
                } else {
                  validChoice = true;
                }
              } else if (choice == 2) {
                if (loaded && !deallocated) {
                  System.out.println("Job " + (anotherCounter + 1) + " Memory deallocated successfully.");
                  previousMemory = availableMemory;
                  availableMemory += jobs[anotherCounter];
                  deallocated = true;
                  loaded = false;
                  System.out.println("\nAllocate or Deallocate?");
                  System.out.print("Allocate(1) Deallocate(2): ");
                  
                } else if (deallocated) {
                  System.out.println("Deallocation failed. Partition is not handling a job.");
                  Thread.sleep(1250);
                  header();
                  System.out.println("Enter memory capacity: " + memory);
                  System.out.println("Enter OS Size(K): " + size);
                  jobsError(jobs, counter);
                  jobListError();
                  System.out.println("\nAllocating Job " + (anotherCounter + 1) + "...");
                  System.out.println("Job " + (anotherCounter + 1) + " Memory allocated successfully.");
                  System.out.println("OS(" + size + "K) Job " + (anotherCounter + 1) + "("
                      + jobs[anotherCounter] + "K) Unused Space(" + previousMemory + "K)");
                  restartInput();
                  System.out.println("2\nJob " + (anotherCounter + 1) + " Memory deallocated successfully.");
                  System.out.println("\nAllocate or Deallocate?");
                  System.out.print("Allocate(1) Deallocate(2): ");

                } else {
                  System.out.println("Deallocation failed. Partition is not handling a job.");
                  Thread.sleep(1250);
                  header();
                  System.out.println("Enter memory capacity: " + memory);
                  System.out.println("Enter OS Size(K): " + size);
                  jobsError(jobs, counter);
                  jobListError();
                  System.out.println("\nAllocating Job " + (anotherCounter + 1) + "...");
                  System.out.println("Job " + (anotherCounter + 1)
                      + " Memory failed to load. Insufficient memory space for allocation.");
                  System.out.println("OS(" + size + "K) Job " + (anotherCounter + 1) + "("
                      + jobs[anotherCounter] + "K) Unused Space(" + availableMemory + "K)");
                  restartInput();
                }
              }
            } else {
              System.out.println("Invalid input. Choose from one of the options only.");
              Thread.sleep(1250);
              header();
              System.out.println("Enter memory capacity: " + memory);
              System.out.println("Enter OS Size(K): " + size);
              jobsError(jobs, counter);
              jobListError();
              System.out.println("\nAllocating Job " + (anotherCounter + 1) + "...");
              System.out.println("Job " + (anotherCounter + 1) + " Memory allocated successfully.");
              System.out.println("OS(" + size + "K) Job " + (anotherCounter + 1) + "(" + jobs[anotherCounter]
                  + "K) Unused Space(" + availableMemory + "K)");
              restartInput();
            }

          } catch (InputMismatchException ex) {
            validChoice = false;
            System.out.println("Invalid input. Please enter whole number or a valid number only.");
            Thread.sleep(2000);
            header();
            System.out.println("Enter memory capacity: " + memory);
            System.out.println("Enter OS Size(K): " + size);
            jobsError(jobs, counter);
            jobListError();
            System.out.println("\nAllocating Job " + (anotherCounter + 1) + "...");
            System.out.println("Job " + (anotherCounter + 1) + " Memory allocated successfully.");
            System.out.println("OS(" + size + "K) Job " + (anotherCounter + 1) + "(" + jobs[anotherCounter]
                + "K) Unused Space(" + availableMemory + "K)");
            restartInput();
          }
        } while (!validChoice);
      } else {
        System.out.print("\nNo more jobs waiting.");
        pressEnterToContinue();
      }

			header();
			System.out.println("Enter memory capacity: " + memory);
			System.out.println("Enter OS Size(K): " + size);
			jobsError(jobs, counter);
			jobListError();
			anotherCounter++;

		} while (anotherCounter < jobsQuantity);

		header();
		System.out.println("Enter memory capacity: " + memory);
		System.out.println("Enter OS Size(K): " + size);
		jobsError(jobs, counter);
		jobListError();

		int jobsAllocated = 0;
		int separator = 0;
		int jobsPrint = 0;

		do {
			if (jobsPrint == 0) {
				System.out.println("\nJob(s) Considered:");
			} else {
				System.out.println("\n\nFailed Job(s):");
			}

			for (int i = 0; i < jobsQuantity; i++) {
				if (separator == 0) {
					if (jobsConsidered[i] == 1 && jobsPrint == 0) {
						System.out.print("Job " + (i + 1) + "(" + jobs[i] + "K), ");
						jobsAllocated++;
						separator++;
					} else if (jobsConsidered[i] == 0 && jobsPrint == 1) {
						System.out.print("Job " + (i + 1) + "(" + jobs[i] + "K), ");
						separator++;
					}
				} else if (separator == 1) {
					if (jobsConsidered[i] == 1 && jobsPrint == 0) {
						System.out.print("Job " + (i + 1) + "(" + jobs[i] + "K)");
						jobsAllocated++;
						separator++;
					} else if (jobsConsidered[i] == 0 && jobsPrint == 1) {
						System.out.print("Job " + (i + 1) + "(" + jobs[i] + "K)");
						separator++;
					}
				} else {
					if (jobsConsidered[i] == 1 && jobsPrint == 0) {
						System.out.print(", Job " + (i + 1) + "(" + jobs[i] + "K)");
						jobsAllocated++;
					} else if (jobsConsidered[i] == 0 && jobsPrint == 1) {
						System.out.print(", Job " + (i + 1) + "(" + jobs[i] + "K)");
					}
				}
			}
			separator = 0;
			jobsPrint++;

		} while (jobsPrint < 2);

		System.out
				.println("\n\nConclusion: There are only " + jobsAllocated + " job(s) considered for memory allocation.");
	}
}