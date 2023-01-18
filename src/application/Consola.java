package application;

import java.io.File;
import java.util.ArrayList;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Consola {
	@SuppressWarnings("unchecked")
	public static Scene getScene(Deck deck, ArrayList<Card> bot, ArrayList<Card> player, boolean botcheck, Stage s)
			throws Exception {
		BorderPane root = new BorderPane();
		BackgroundImage myBI = new BackgroundImage(
				new Image("https://t4.ftcdn.net/jpg/04/20/86/73/360_F_420867335_srDH3p52ShctnCgpD6XPbdYUR1T0TB9B.jpg",
						1280, 720, false, true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);

		Label casino = new Label("CASINO");
		casino.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		casino.setStyle("-fx-text-fill: white;");
		Label name = new Label("PLAYER");
		name.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		name.setStyle("-fx-text-fill: white;");
		VBox names = new VBox();
		names.getChildren().addAll(casino, name);
		names.setSpacing(250);
		names.setAlignment(Pos.CENTER);

		Button hit = new Button("HIT");
		hit.setFont(Font.font("Arial", 18));
		hit.setPrefSize(120, 120);
		if (!botcheck)
			hit.setDisable(true);
		hit.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				player.add(deck.drawCard());
				try {
					Scene sc = Consola.getScene(deck, bot, player, true, s);
					s.setScene(sc);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		Button stay = new Button("STAY");
		stay.setPrefSize(120, 120);
		stay.setFont(Font.font("Arial", 18));
		if (!botcheck)
			stay.setDisable(true);
		stay.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				try {
					Scene sc = Consola.getScene(deck, bot, player, false, s);
					s.setScene(sc);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		HBox Hbtn = new HBox();
		Hbtn.getChildren().addAll(hit, stay);
		Hbtn.setSpacing(400);
		Hbtn.setPadding(new Insets(0, 120, 0, 0));
		Hbtn.setAlignment(Pos.CENTER);

		int sumbot = Deck.sum(bot, false);

		if (!botcheck) {
			sumbot = Deck.sum(bot, true);
			bot.remove(0);
			while (sumbot < 16) {
				bot.add(deck.drawCard());
				sumbot = Deck.sum(bot, true);
			}
		}
		boolean ok = false;
		int sum = Deck.sum(player, false);
		if (sum > 21 || (!botcheck && sumbot >= sum && sumbot < 22)) {
			stay.setDisable(true);
			hit.setDisable(true);
			Label loser = new Label("YOU LOST");
			loser.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
			loser.setStyle("-fx-text-fill: white;");
			Hbtn.setSpacing(100);
			Hbtn.getChildren().add(1, loser);
			ok = !ok;
		} else if (!botcheck) {
			if (sumbot < sum || sumbot > 21) {
				stay.setDisable(true);
				hit.setDisable(true);
				Label win = new Label("YOU WIN");
				win.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 40));
				win.setStyle("-fx-text-fill: white;");
				Hbtn.setSpacing(100);
				Hbtn.getChildren().add(1, win);
				ok = !ok;
			}
		}
		HBox botCards = Consola.deckShow(bot, botcheck);
		HBox playerCards = Consola.deckShow(player, false);
		if (ok) {
			Button replay = new Button("REPLAY");
			replay.setPrefSize(120, 120);
			replay.setFont(Font.font("Arial", 18));
			replay.setOnAction(new EventHandler() {
				@Override
				public void handle(Event arg0) {
					try {
						bot.removeAll(bot);
						player.removeAll(player);
						Deck deckr = new Deck();
						deckr.shuffle();
						Card cover = new Card("cover", "cover",
								"/home/user/Info/java/BLACKJACK/src/application/cards/cover.png");
						bot.add(cover);
						bot.add(deckr.drawCard());
						bot.add(deckr.drawCard());
						player.add(deckr.drawCard());
						player.add(deckr.drawCard());
						Scene sc = Consola.getScene(deckr, bot, player, true, s);
						s.setScene(sc);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			playerCards.getChildren().add(replay);

		}

		Label scorbot = new Label();
		scorbot.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		scorbot.setStyle("-fx-text-fill: white;");
		scorbot.setText(String.valueOf(sumbot));
		Label scorplayer = new Label();
		scorplayer.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
		scorplayer.setStyle("-fx-text-fill: white;");
		scorplayer.setText(String.valueOf(sum));
		VBox scores = new VBox();
		scores.getChildren().addAll(scorbot, scorplayer);
		scores.setSpacing(250);
		scores.setAlignment(Pos.CENTER);

		root.setCenter(Hbtn);
		root.setBackground(new Background(myBI));
		root.setTop(botCards);
		root.setBottom(playerCards);
		root.setLeft(names);
		root.setRight(scores);
		Scene scene = new Scene(root, 1280, 720);
		return scene;
	}

	public static HBox deckShow(ArrayList<Card> hand, boolean bot) throws Exception {
		HBox h = new HBox();
		if (bot) {
			ImageView v = Consola.imgCard(hand.get(1));
			h.getChildren().add(v);
			v = Consola.imgCard(hand.get(0));
			h.getChildren().add(v);
		} else {
			for (Card i : hand) {
				ImageView v = Consola.imgCard(i);
				h.getChildren().add(v);
			}
		}
		h.setSpacing(10);
		h.setAlignment(Pos.CENTER);
		return h;
	}

	public static ImageView imgCard(Card c) throws Exception {
		File carte = new File(c.getUrl());
		Image card = new Image(carte.toURI().toURL().toString());
		ImageView car = new ImageView(card);
		car.setFitWidth(120);
		car.setFitHeight(180);
		return car;
	}

}
