import java.util.ArrayList;
import java.util.Scanner;

public class Unilink {
	private static Scanner sc;
	private static Post post = null;
	private static ArrayList<Post> postList = new ArrayList<Post>();

	private static ArrayList<EventPost> eventList = new ArrayList<EventPost>();
	private static ArrayList<SalePost> saleList = new ArrayList<SalePost>();
	private static ArrayList<JobPost> jobList = new ArrayList<JobPost>();

	private static String username = "";
	private static String postid;
	private static String postName = "";
	private static String postDescription = "";
	private static String eventVenue;
	private static String eventDate;
	private static int eventCapacity;
	public static String eventStatus;
	private static int eventNo = 1;

	private static double askingPrice = 0.0;
	private static double minimumRaise = 0.0;
	private static String saleStatus = "";
	private static int saleNo = 1;

	private static double proposedPrice = 0.0;
	private static int jobNo = 1;
	private static String jobStatus = "";
	private static String replyId;

	// Login function to login into the Unilink system
	public static boolean login() {
		boolean flag;
		int loginChoice = 0;
		System.out.println("WELCOME TO UNILINK");
		do {
			try {
				sc = new Scanner(System.in);
				System.out.println("\t1. Login\n\t2. Quit");
				loginChoice = sc.nextInt();
				flag = false;
			} catch (Exception e) {
				System.out.println("Enter valid option");
				flag = true;
			}
		} while (flag);

		if (loginChoice == 1) {
			System.out.print("Enter Username: ");
			username = sc.next();
			// validate username "starts with s and followed by number"
			while (!username.matches("^[s][0-9]+")) {
				System.out.println("Enter a valid user name:");
				username = sc.next();
			}
			return true;
		} else if (loginChoice > 2) {
			System.out.println("Enter a valid code");
			login();
		}
		return false;
	}

	public static void postDetail() {
		sc = new Scanner(System.in);
		do {
			System.out.print("Post Name: ");
			postName = sc.nextLine();
		} while (postName.isEmpty());
		do {
			System.out.print("Post Description: ");
			postDescription = sc.nextLine();
		} while (postDescription.isEmpty());
	}

	private static void createNewEventpost() {
		boolean flag;

		do {
			System.out.print("Event Venue: ");
			eventVenue = sc.nextLine();
		} while (eventVenue.isEmpty());
		do {
			System.out.print("Event Date: ");
		} while (!validateDate(getDate()));
		eventStatus = "OPEN";
		do {
			do {
				try {
					sc = new Scanner(System.in);
					System.out.print("Event Max-Capacity: ");
					eventCapacity = sc.nextInt();
					flag = false;
				} catch (Exception e) {
					System.out.println("Enter valid input");
					flag = true;
				}
			} while (flag);

			if (eventCapacity < 1) {
				System.out.println("Enter a valid response.");
			}
		} while (eventCapacity == 0 && eventCapacity < 1);
		postid = "EVE" + String.format("%03d", eventNo);
		post = new EventPost(postid, postName, postDescription, username, eventStatus, eventVenue, eventDate,
				eventCapacity, 0);
		postList.add(post);
		eventList.add((EventPost) post);
		System.out.print("\nYour Event has been successfully created with Id: " + postid + "\n");
		eventNo++;
	}

	public static String getDate() {
		do {
			eventDate = sc.nextLine();
			if (!eventDate.matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$")) {
				System.out.println("Kindly enter a valid date:");
			}
		} while (!eventDate.matches("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$"));
		return eventDate;
	}

	// function to validate date
	public static boolean validateDate(String date) {
		String dateV[] = date.split("/");
		int day = Integer.parseInt(dateV[0]);
		int month = Integer.parseInt(dateV[1]);
		boolean dateAccept = false;
		boolean isleap = ((Integer.parseInt(dateV[2]) % 4 == 0) && (Integer.parseInt(dateV[2]) % 100 != 0)
				|| (Integer.parseInt(dateV[2]) % 400 == 0));
		// checking if it is a leap year or not to accept the date for month FEB
		if (isleap) {
			if (Integer.parseInt(dateV[1]) == 02) {
				if (Integer.parseInt(dateV[0]) <= 29) {
					dateAccept = true;
				}
			}
		} else if (Integer.parseInt(dateV[1]) == 02) {
			if (Integer.parseInt(dateV[0]) <= 28) {
				dateAccept = true;
			}
		}
		// to check if the days are entered properly
		if ((Integer.parseInt(dateV[1]) == 1 || Integer.parseInt(dateV[1]) == 3 || Integer.parseInt(dateV[1]) == 5
				|| Integer.parseInt(dateV[1]) == 7 || Integer.parseInt(dateV[1]) == 8
				|| Integer.parseInt(dateV[1]) == 10 || Integer.parseInt(dateV[1]) == 12)
				&& Integer.parseInt(dateV[1]) != 02) {
			if (Integer.parseInt(dateV[0]) <= 31) {
				dateAccept = true;
			}
		} else if ((Integer.parseInt(dateV[0]) <= 30) && Integer.parseInt(dateV[1]) != 02) {
			dateAccept = true;

		}
		if (day > 31 || month > 12) {
			System.out.println("Enter an acceptable date.");
			dateAccept = false;
		}
		return dateAccept;
	}

	// function to handle reply to an event
	private static void eventReply(String replyId) {
		double value = 0;
		boolean flag;
		for (int i = 0; i < postList.size(); i++) {
			if (postList.get(i).getId().equals(replyId)) {
				if (postList.get(i).getCreator_id().equalsIgnoreCase(username)) {
					System.out.println("Cannot reply to your own post.");
				} else {
					for (int j = 0; j < eventList.size(); j++) {
						if (replyId.equals(eventList.get(j).getId())) {
							System.out.println(eventList.get(j).getEventDetail());
							do {
								try {
									System.out.print("Are you attaneding or not (1- yes/ 0- no): ");
									value = sc.nextDouble();
									flag = false;
								} catch (Exception e) {
									System.out.println("Enter a valid option");
									flag = true;
								}
							} while (flag);

							if (value == 1) {
								Reply reply = new Reply(replyId, value, username);
								if (postList.get(i).handleReply(reply)) {
									postList.set(i, postList.get(i));
									System.out.println("Response Succesfully registered.");
								} else {
									System.out.println("Cannot register");
								}
							}
						}
					}
				}
			}
		}
	}

	public static void createNewSalePost() {
		boolean flag;
		System.out.println("\t**Enter Your Sale Details**");
		do {
			do {
				try {
					sc = new Scanner(System.in);
					System.out.print("Asking Price( in $ ): ");
					askingPrice = sc.nextDouble();
					flag = false;
				} catch (Exception e) {
					System.out.println("Enter amount");
					flag = true;
				}
			} while (flag);

		} while (askingPrice == 0);
		do {
			do {
				try {
					System.out.print("Minimum Raise (in $): ");
					minimumRaise = sc.nextDouble();
					flag = false;
				} catch (Exception e) {
					System.out.println("Enter amount");
					flag = true;
				}
			} while (flag);

		} while (minimumRaise == 0);
		saleStatus = "OPEN";
		postid = "SAL" + String.format("%03d", saleNo);
		post = new SalePost(postid, postName, postDescription, username, saleStatus, askingPrice, minimumRaise, 0);
		postList.add(post);
		saleList.add((SalePost) post);
		System.out.println("Your Sale has been successfully created with id: " + postid);
		saleNo++;
	}

	private static void saleReply(String replyId) {
		double value = 0;
		boolean flag;
		for (int i = 0; i < postList.size(); i++) {
			if (postList.get(i) instanceof SalePost) {
				if (postList.get(i).getId().equals(replyId)) {
					if (postList.get(i).getCreator_id().equalsIgnoreCase(username)) {
						System.out.println("Cannot reply to your own post");
					} else {
						for (int j = 0; j < saleList.size(); j++) {
							if (saleList.get(j).getId().equals(replyId)) {
								System.out.println(saleList.get(j).getSaleDetail());
							}
						}
						do {
							do {
								try {
									System.out.println("Enter your offer price: ");
									value = sc.nextDouble();
									flag = false;
								} catch (Exception e) {
									System.out.println("Enter amount");
									flag = true;
								}
							} while (flag);

						} while (value == 0);
						Reply reply = new Reply(replyId, value, username);
						if (postList.get(i).handleReply(reply)) {
							System.out.println("Your offer is accepted.");
						} else {
							System.out.println("Reply not stored");
						}
					}
				}
			}
		}
	}

	public static void createNewJobPost() {
		boolean flag;
		System.out.println("\t**Enter Your Job Details**");
		do {
			do {
				try {
					sc = new Scanner(System.in);
					System.out.print("Proposed Price: ");
					proposedPrice = sc.nextDouble();
					flag = false;
				} catch (Exception e) {
					System.out.println("Enter amount");
					flag = true;
				}
			} while (flag);

		} while (proposedPrice == 0);
		postid = "JOB" + String.format("%03d", jobNo);
		jobStatus = "OPEN";
		post = new JobPost(postid, postName, postDescription, username, jobStatus, proposedPrice);
		postList.add(post);
		jobList.add((JobPost) post);
		System.out.print("Your Job has been successfully created\n");
		jobNo++;
	}

	private static void jobReply(String replyId) {
		double value;
		for (int i = 0; i < postList.size(); i++) {
			if (postList.get(i) instanceof JobPost) {
				if (postList.get(i).getId().equals(replyId)) {
					if (postList.get(i).getCreator_id().equalsIgnoreCase(username)) {
						System.out.println("Cannot reply to your own post");
					} else {
						System.out.println("Enter your offer price: ");
						value = sc.nextDouble();
						Reply reply = new Reply(replyId, value, username);
						if (postList.get(i).handleReply(reply)) {
							System.out.println("Your Offer is accepted.");
						} else {
							System.out.println("Your offer is higher than the proposed price.");
						}
					}
				}
			}
		}
	}

	public static void startup() {
		boolean logout = true;
		boolean valid;
		if (login()) {
			int studentMenu = 0;
			System.out.println("Welcome " + username + "!\n");
			do {

				do {
					try {
						System.out.print("** Student Menu **\n" + "1. New event post\n" + "2. New Sale post\n"
								+ "3. New Job post" + "\n4. Reply To post\n" + "5. Display My posts\n"
								+ "6. Display All posts\n" + "7. Close post\n" + "8. Delete post\n" + "9. Log Out\n"
								+ "Enter your choice: ");
						sc = new Scanner(System.in);
						studentMenu = sc.nextInt();
						valid = false;
					} catch (Exception e) {
						System.out.println("\t **Enter a Valid Input**");
						valid = true;
					}
				} while (valid);
				switch (studentMenu) {
				case 1:
					postDetail();
					createNewEventpost();
					break;
				case 2:
					postDetail();
					createNewSalePost();
					break;
				case 3:
					postDetail();
					createNewJobPost();
					break;
				case 4: {
					sc = new Scanner(System.in);
					boolean postAvail = true;
					if (postList.size() != 0) {
						do {
							System.out.print("Enter post ID : ");
							replyId = sc.nextLine();
						} while (replyId.isEmpty());
						// check if post is available in the list
						for (int i = 0; i < postList.size(); i++) {
							if (!(postList.get(i).getId().equals(postid))) {
								postAvail = false;
							} else {
								postAvail = true;
								break;
							}
						}
						if (postAvail) {
							for (int i = 0; i < postList.size(); i++) {
								if (postList.get(i).getId().equals(replyId)) {
									if (postList.get(i) instanceof EventPost) {
										eventReply(replyId);
										break;
									} else if (postList.get(i) instanceof SalePost) {
										saleReply(replyId);
										break;
									} else if (postList.get(i) instanceof JobPost) {
										jobReply(replyId);
										break;
									}
								}
							}
						} else {
							System.out.println("No post with that Id exist");
						}
					} else {
						System.out.println("There are no Post in the system at the moment");
					}
				}
					break;
				case 5: {
					System.out.println("Your posts\n");
					for (int i = 0; i < postList.size(); i++) {
						if (postList.get(i).getCreator_id().equals(username)) {
							System.out.println(postList.get(i).getReplyDetails());
							System.out.println("--------------------");
						}
					}
					break;
				}
				case 6:
					System.out.println("\t\t** All Posts **");
					for (int i = 0; i < postList.size(); i++) {
						System.out.println("--------------------");
						System.out.println(postList.get(i).getPostDetails());
					}
					break;

				case 7: {
					sc = new Scanner(System.in);
					String postId;
					double lowestValue;
					int lowestValueId = 0;
					do {
						System.out.print("Enter the Post Id to close: ");
						postId = sc.nextLine();
					} while (postid.isEmpty());
					for (int i = 0; i < postList.size(); i++) {
						if (postList.get(i).getId().equals(postId)) {
							if (postList.get(i) instanceof JobPost) {
								System.out.println("Do you wish to close the Job Post? ( 1 - Yes / 0 - No)");
								int opt = sc.nextInt();
								if (opt == 1) {
									if (postList.get(i).getCreator_id().equals(username)) {
										if (postList.get(i).getStatus().equalsIgnoreCase("closed")) {
											System.out.println("Post already Closed");
										} else {
											postList.get(i).setStatus("CLOSED");
											/*
											 * finding the lowest value of the job post and assigning the job to that
											 * user after closing the post
											 */
											for (int j = 0; j < post.getReplies().size(); j++) {
												lowestValue = post.getReplies().get(j).getValue();
												if (lowestValue < post.getReplies().get(j).getValue()) {
													lowestValue = post.getReplies().get(j).getValue();
													lowestValueId = j;
												}
											}
											System.out.println("The job has been assigned to: "
													+ post.getReplies().get(lowestValueId).getResponder_id());
											postList.set(i, postList.get(i));
											System.out.println("Post Successfully Closed.");
											break;
										}
									} else {
										System.out.println("You can only Close / Delete Your own post.");
									}
								}
							} else {
								if (postList.get(i).getCreator_id().equals(username)) {
									postList.get(i).setStatus("CLOSED");
									System.out.println("POst closed succesfully");
								} else {
									System.out.println("You cannot close other users post");
								}
							}
						}
					}
					break;
				}
				case 8:
					sc = new Scanner(System.in);
					String postId;
					if (postList.size() != 0) {
						do {
							System.out.println("Enter the post to delete:");
							postId = sc.nextLine();
						} while (postId.isEmpty());
						for (int i = 0; i < postList.size(); i++) {
							if (postList.get(i).getId().equals(postId)) {
								if (postList.get(i).getCreator_id().equals(username)) {
									if (postList.get(i) instanceof EventPost) {
										for (int e = 0; e < eventList.size(); e++) {
											if (eventList.get(e).getId().equals(postId)) {
												eventList.remove(e);
												System.out.println("Event deleted");
											}
										}
									}
									if (postList.get(i) instanceof SalePost) {
										for (int s = 0; s < eventList.size(); s++) {
											if (saleList.get(s).getId().equalsIgnoreCase(postId)) {
												saleList.remove(s);
												System.out.println("Sale deleted");
											}
										}
									}
									if (postList.get(i) instanceof JobPost) {
										for (int j = 0; j < eventList.size(); j++) {
											if (jobList.get(j).getId().equalsIgnoreCase(postId)) {
												jobList.remove(j);
												System.out.println("Job deleted");
											}
										}
									}
									postList.remove(i);
								} else {
									System.out.println(
											"You are not the owner of the post. Permission to delete is denied.");
								}
							}
						}
						System.out.println("Post " + postId + " does not exist");
					} else {
						System.out.println("There is no Post in the system at the moment");
					}
					break;
				case 9:
					startup();
					break;
				default:
					System.out.println("Enter valid menu option:\n");
				}
			} while (studentMenu < 9);
		} else {
			System.out.println("\t\t** Thank you for using UniLink System **");
			logout = false;
		}

	}

	// Hardcoding the default values while the System starts
	public static void hardcode() {
		Reply reply;
		// ----------------- HardCode Event Post --------------------------
		postid = "EVE" + String.format("%03d", eventNo);
		post = new EventPost(postid, "Pizza Party", "Make friends and enjoy unlimited pizzas", "s1", "OPEN",
				"RMIT Building 80", "24/04/2020", 10, 3);

		reply = new Reply(postid, 1.0, "s2");
		post.handleReply(reply);

		reply = new Reply(postid, 1.0, "s4");
		post.handleReply(reply);

		reply = new Reply(postid, 1.0, "s6");
		post.handleReply(reply);

		postList.add(post);
		eventList.add((EventPost) post);
		eventNo++;
		// ----------------- HardCode Sale Post --------------------------
		postid = "SAL" + String.format("%03d", saleNo);
		post = new SalePost(postid, "Samsung Galaxy Note 5", "New phone. Good working condition.", "s3", "OPEN", 600.0,
				100.0, 0);
		reply = new Reply(postid, 250, "s6");
		post.handleReply(reply);

		reply = new Reply(postid, 450, "s9");
		post.handleReply(reply);

		postList.add(post);
		saleList.add((SalePost) post);
		saleNo++;

		// ----------------- HardCode Job Post --------------------------
		postid = "JOB" + String.format("%03d", jobNo);
		post = new JobPost(postid, "Website Development", "Technology - React", "s9", "OPEN", 35.0);
		reply = new Reply(postid, 40.0, "s4");
		post.handleReply(reply);

		reply = new Reply(postid, 25.0, "s4");
		post.handleReply(reply);

		reply = new Reply(postid, 35.0, "s6");
		post.handleReply(reply);

		postList.add(post);
		jobList.add((JobPost) post);
		jobNo++;

	}

}