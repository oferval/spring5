package org.oferval.Asynchronous;

import lombok.extern.slf4j.Slf4j;
import org.oferval.Asynchronous.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;

@SpringBootApplication
@Slf4j
public class AsynchronousApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsynchronousApplication.class, args);
	}


	//@Bean
	public CommandLineRunner futureChain (){
		return (args -> {

			CompletableFuture<Void> completableFuture =

			CompletableFuture.supplyAsync( () -> {
							goSleep(10000);
							return Arrays.asList(1L, 2L, 3L);})
					.thenApply(this::readUsers)
					.thenAccept(users -> log.info(format("Chain* UserSize: %s" , users.size())))
					.thenRun(() -> log.info("Chain* Users read."));

			log.info("Chain* Waiting the to end the CommanLineRunner.");
			while (!completableFuture.isDone());

			log.info("Chain* End.");

		});
	}

	//@Bean
	public CommandLineRunner futureComposition (){
		return (args -> {

			CompletableFuture<List<Long>> completableFuture =
			CompletableFuture.supplyAsync( () -> {
							log.info("Compo* reading users IDs.");
							goSleep(10000);
							return Arrays.asList(1L, 2L, 3L);});

			//These both task will run in parallel.
			// Same result using completableFuture.thenApply(this::readUsers)
			completableFuture.thenCompose(this::readUsersAsync)
					.thenRun(() -> log.info("Compo* Users read."));
			completableFuture.thenAccept(users -> log.info(String.format("Compo* UserSize: %s" , users.size())));

			log.info("Compo* Waiting main");
			goSleep(2000);
			log.info("Compo* End main.");

		});
	}

	//@Bean
	public CommandLineRunner futureCombining (){
		return (args -> {

			CompletableFuture<List<Long>> getIdsFuture =
					CompletableFuture.supplyAsync( () -> {
						log.info("Combing* reading users IDs.");
						goSleep(1000);
						return Arrays.asList(1L, 2L, 3L);});

			CompletableFuture<List<String>> getNamesFuture =
					CompletableFuture.supplyAsync( () -> {
						log.info("Combing* reading users Names.");
						goSleep(1000);
						return Arrays.asList("NameID1", "NameID2", "NameID3");});

			getIdsFuture.thenAcceptBoth(getNamesFuture, (listIds, listNames) ->
					log.info(String.format("Combing* both Futures %s and %s", listIds.get(0), listNames.get(0))));

			log.info("Combing* Waiting main");
			goSleep(10000);
			log.info("Combing* End main.");

		});
	}

	//@Bean
	public CommandLineRunner futureEither (){
		return (args -> {

			CompletableFuture<List<Long>> getIdsFutureSlow =
					CompletableFuture.supplyAsync( () -> {
						log.info("Either* Slow reading users IDs.");
						goSleep(3000);
						return Arrays.asList(1L, 2L, 3L);});

			CompletableFuture<List<Long>> getIdsFutureFast =
					CompletableFuture.supplyAsync( () -> {
						log.info("Either* Fast reading users IDs.");
						goSleep(1000);
						return Arrays.asList(4L, 6L, 6L);});;

			getIdsFutureSlow.acceptEither(getIdsFutureFast, getIdsFuture ->
					log.info(String.format("Either* Futures %s", getIdsFuture.get(0))));

			log.info("Either* Waiting main");
			goSleep(10000);
			log.info("Either* End main.");

		});
	}

	@Bean
	public CommandLineRunner futureN_allOf (){
		return (args -> {

			CompletableFuture<List<Long>> getIdsFuture =
					CompletableFuture.supplyAsync( () -> {
						log.info("Combing* reading users IDs.");
						goSleep(1000);
						return Arrays.asList(1L, 2L, 3L);});

			CompletableFuture<List<String>> getNamesFuture =
					CompletableFuture.supplyAsync( () -> {
						log.info("Combing* reading users Names.");
						goSleep(1000);
						return Arrays.asList("NameID1", "NameID2", "NameID3");});

			CompletableFuture.allOf(getIdsFuture, getNamesFuture);

			log.info("Either* Waiting main");
			goSleep(10000);
			log.info("Either* End main.");

		});
	}

	private List<User> readUsers(List<Long> listUsersIds) {
		List<User> userList = new ArrayList<User>();

		listUsersIds.forEach( userId -> {
			goSleep(1000);
			log.info(format("Chain* User: %s", userId));
			userList.add(new User(userId));
		});

		return userList;
	}

	private CompletableFuture<List<User>> readUsersAsync (List<Long> listUsersIds) {
		List<User> userList = new ArrayList<User>();

		return CompletableFuture.supplyAsync( () -> {

				listUsersIds.forEach( userId -> {
					goSleep(1000);
					log.info(format("Compo* reading user: %s", userId));
					userList.add(new User(userId));
				});
				return userList;
		});
	}

	private void goSleep(int time) {
		try {
				Thread.sleep(time);
			} catch (InterruptedException ignored) {}
	}

	private void logIDs (List<Long> listUsersIds){
		listUsersIds.forEach( id -> {
			log.info("element:" + id + " ");
			goSleep(1000);
		});
	}

}

