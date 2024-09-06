package com.noodle;

import java.lang.Math;
import java.lang.Thread;
import java.io.*;
 
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Intersector;

public class MainGame extends Game {
	
	int buttonTextWidth;
	int buttonTextHeight;
	int buttonImageWidth;
	int windowNoTitleSize;
	int windowNoTitlePadding;
	TextButton.TextButtonStyle textButtonStyle;
	ScreenViewport screenViewport;
	int imageToButtonPercent = 75;
	MainMenu mainMenuScreen;
	Loading loadingScreen;
	GameScreen gameScreen;
	LoadingGame loadingGameScreen;
	AssetManager assetManager;
	BitmapFont daySansVeryLarge;
	BitmapFont daySansLarge;
	BitmapFont daySansMedium;
	BitmapFont daySansSmall;
	BitmapFont joystix;
	int skinIndex = 0;
	int skinSelected = skinIndex;
	int noodleIndex = 0;
	int noodleSelected = skinIndex;
	//TextureRegion[] beachFrames;
	TextureRegion[] whiteFanFrames;
	TextureRegion[] blackFanFrames;
	TextureRegion[] human0Frames;
	TextureRegion[] human00Frames;
	TextureRegion[] human000Frames;
	TextureRegion[] human01Frames;
	TextureRegion[] human02Frames;
	TextureRegion[] human03Frames;
	TextureRegion[] human1Frames;
	TextureRegion[] human10Frames;
	TextureRegion[] human100Frames;
	TextureRegion[] human11Frames;
	TextureRegion[] human12Frames;
	TextureRegion[] human13Frames;
	TextureRegion[] catIdleFrames;
	TextureRegion[] catRunFrames;
	TextureRegion[] dogIdleFrames;
	TextureRegion[] dogRunFrames;
	CheckBox TILT;
	CheckBox SOUND;
	int HScore = 0;
	String username = "web";
		String server = "files.000webhost.com";
        int port = 21;
        String user = "needlenoodle";
        String pass = "Pr@040303";
	int serverHighest = 0;
	boolean mManWhite;
	boolean mPetCat;
	TextureRegion[] mPetFrames;
	TextureRegion[] mManFrames;
	int mWallIndex;
	int mFloorIndex;
	
	@Override
	public void create () {
		screenViewport = new ScreenViewport();
			
			assetManager = new AssetManager();
			assetManager.load("daysans_very_large.fnt", BitmapFont.class);
			assetManager.finishLoading();
			
		setScreen(mainMenuScreen);
	}
	
	public Animation<TextureRegion> getAnimationfromTexture(Texture walkSheet, int FRAME_ROWS, int FRAME_COLS, float nSpeed, Animation.PlayMode playMode)
	{
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS,
				walkSheet.getHeight() / FRAME_ROWS);

		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		Animation<TextureRegion> animation = new Animation<TextureRegion>(nSpeed, walkFrames);
		animation.setPlayMode(playMode);
		return animation;
	}
 
	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		super.render();
		if(loadingScreen==null && assetManager.update())
		{
			daySansVeryLarge = assetManager.get("daysans_very_large.fnt", BitmapFont.class);
			daySansVeryLarge.getData().setScale((float)Gdx.graphics.getHeight()/1080f);
			
			loadingScreen = new Loading(this);
			setScreen(loadingScreen);
		}
	}
	
	@Override
	public void dispose () {
		/*logoText.dispose();
		daySansLarge.dispose();
		buttonText.dispose();
		buttonTextDown.dispose();
		buttonImage.dispose();
		buttonImageDown.dispose();
		settingsImg.dispose();
		characterImg.dispose();
		click.dispose();*/
	}
	
	public class Loading implements Screen{
		
		Game game;
		Stage loading;
		
		public Loading(Game game)
		{
			this.game = game;
			
			loading = new Stage(screenViewport);
			
			Label.LabelStyle loadingStyle = new Label.LabelStyle();
			loadingStyle.font = daySansVeryLarge;
			
			Label LOADING = new Label("LOADING...", loadingStyle);
			LOADING.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			LOADING.setAlignment(Align.center);
			loading.addActor(LOADING);
			//LOADING.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		}
		
		public void show ()
		{
					assetManager.load("daysans_large.fnt", BitmapFont.class);
					assetManager.load("daysans_medium.fnt", BitmapFont.class);
					assetManager.load("daysans_small.fnt", BitmapFont.class);
					assetManager.load("joystix.fnt", BitmapFont.class);
					assetManager.load("button_text.png", Texture.class);
					assetManager.load("button_text_down.png", Texture.class);
					assetManager.load("click.wav", Sound.class);
					assetManager.load("select.wav", Sound.class);
					assetManager.load("close.png", Texture.class);
					assetManager.load("close_down.png", Texture.class);
					assetManager.load("window_no_title.png", Texture.class);
					assetManager.load("black_transparent_screen.png", Texture.class);
					assetManager.load("black_transparent_screen_full.png", Texture.class);
					assetManager.load("logo_text.png", Texture.class);
					assetManager.load("settings.png", Texture.class);
					assetManager.load("settings_down.png", Texture.class);
					assetManager.load("noodle0.bmp", Texture.class);
					assetManager.load("noodle0_selected.bmp", Texture.class);
					assetManager.load("noodle1.bmp", Texture.class);
					assetManager.load("noodle1_selected.bmp", Texture.class);
					assetManager.load("noodle2.bmp", Texture.class);
					assetManager.load("noodle2_selected.bmp", Texture.class);
					assetManager.load("noodle3.bmp", Texture.class);
					assetManager.load("noodle3_selected.bmp", Texture.class);
					assetManager.load("noodle4.bmp", Texture.class);
					assetManager.load("noodle4_selected.bmp", Texture.class);
					assetManager.load("noodle5.bmp", Texture.class);
					assetManager.load("noodle5_selected.bmp", Texture.class);
					assetManager.load("noodle6.bmp", Texture.class);
					assetManager.load("noodle6_selected.bmp", Texture.class);
					assetManager.load("noodle7.bmp", Texture.class);
					assetManager.load("noodle7_selected.bmp", Texture.class);
					assetManager.load("skin0.bmp", Texture.class);
					assetManager.load("skin0_selected.bmp", Texture.class);
					assetManager.load("skin1.bmp", Texture.class);
					assetManager.load("skin1_selected.bmp", Texture.class);
					assetManager.load("skin2.bmp", Texture.class);
					assetManager.load("skin2_selected.bmp", Texture.class);
					assetManager.load("skin3.bmp", Texture.class);
					assetManager.load("skin3_selected.bmp", Texture.class);
					assetManager.load("skin4.bmp", Texture.class);
					assetManager.load("skin4_selected.bmp", Texture.class);
					assetManager.load("skin5.bmp", Texture.class);
					assetManager.load("skin5_selected.bmp", Texture.class);
					assetManager.load("button_image.png", Texture.class);
					assetManager.load("button_image_down.png", Texture.class);
					assetManager.load("loading0.png", Texture.class);
					assetManager.load("on.png", Texture.class);
					assetManager.load("off.png", Texture.class);
					
					//assetManager.load("beach/0001_0.png", Texture.class);
					//for(int i=1;i<82;i++)
						//assetManager.load((i+1<10?"beach/000":(i+1<100?"beach/00":"beach/0"))+String.valueOf(i+1)+"_4"+".png", Texture.class);

					mManWhite = false;
					mPetCat = MathUtils.random.nextBoolean();
					for(int i=0;i<31;i++)
						assetManager.load("human"+(mManWhite?"1/" : "0/")+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
					for(int i=0;i<31;i++)
						assetManager.load((mPetCat?"cat" : "dog")+"Idle/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
		
					mWallIndex = MathUtils.random.nextInt(3);
					mFloorIndex = MathUtils.random.nextInt(3);
					assetManager.load("wall"+String.valueOf(mWallIndex)+".jpg", Texture.class);
					assetManager.load("floor"+String.valueOf(mFloorIndex)+".png", Texture.class);
		}

		public void render(float delta)
		{
			loading.act(Gdx.graphics.getDeltaTime());
			loading.draw();
					if(textButtonStyle==null && assetManager.update())
					{
						//getUserName();
						/*beachFrames = new TextureRegion[82];
						beachFrames[0] = new TextureRegion(assetManager.get("beach/0001_0.png", Texture.class));
						for(int i=1;i<82;i++)
							beachFrames[i] = new TextureRegion(assetManager.get((i+1<10?"beach/000":(i+1<100?"beach/00":"beach/0"))+String.valueOf(i+1)+"_4"+".png", Texture.class));	
						*/
						buttonTextHeight = Gdx.graphics.getHeight()*75/100/4;
						buttonTextWidth = buttonTextHeight*assetManager.get("button_text.png", Texture.class).getWidth()/assetManager.get("button_text.png", Texture.class).getHeight();
						buttonImageWidth = buttonTextHeight;
						windowNoTitleSize = Gdx.graphics.getHeight()*75/100;
						windowNoTitlePadding = windowNoTitleSize/10;
						
						daySansMedium = assetManager.get("daysans_medium.fnt", BitmapFont.class);
						daySansLarge = assetManager.get("daysans_large.fnt", BitmapFont.class);
						daySansSmall = assetManager.get("daysans_small.fnt", BitmapFont.class);
						joystix = assetManager.get("joystix.fnt", BitmapFont.class);
						daySansMedium.getData().setScale((float)Gdx.graphics.getHeight()/1080f);
						daySansLarge.getData().setScale((float)Gdx.graphics.getHeight()/1080f);
						daySansSmall.getData().setScale((float)Gdx.graphics.getHeight()/1080f);
						joystix.getData().setScale((float)Gdx.graphics.getHeight()/1080f);
						
						textButtonStyle = new TextButton.TextButtonStyle();
						textButtonStyle.font = daySansLarge;
						textButtonStyle.up = new TextureRegionDrawable(assetManager.get("button_text.png", Texture.class));
						textButtonStyle.down = new TextureRegionDrawable(assetManager.get("button_text_down.png", Texture.class));
						textButtonStyle.over = new TextureRegionDrawable(assetManager.get("button_text_down.png", Texture.class));
						
						mPetFrames = new TextureRegion[31];
						for(int i=0;i<31;i++)
							mPetFrames[i] = new TextureRegion(assetManager.get((mPetCat?"cat" : "dog")+"Idle/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
						mManFrames = new TextureRegion[31];
						for(int i=0;i<31;i++)
							mManFrames[i] = new TextureRegion(assetManager.get("human"+(mManWhite?"1/" : "0/")+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
									
						mainMenuScreen = new MainMenu(game);
						setScreen(mainMenuScreen);
						
					assetManager.load("wall0.jpg", Texture.class);
					assetManager.load("wall1.jpg", Texture.class);
					assetManager.load("wall2.jpg", Texture.class);
					assetManager.load("floor0.png", Texture.class);
					assetManager.load("floor1.png", Texture.class);
					assetManager.load("floor2.png", Texture.class);
					assetManager.load("floor0door0.png", Texture.class);
					assetManager.load("floor0door1.png", Texture.class);
					assetManager.load("floor1door0.png", Texture.class);
					assetManager.load("floor1door1.png", Texture.class);
					assetManager.load("floor2door0.png", Texture.class);
					assetManager.load("floor2door1.png", Texture.class);
					assetManager.load("floor0window0.png", Texture.class);
					assetManager.load("floor0window1.png", Texture.class);
					assetManager.load("floor1window0.png", Texture.class);
					assetManager.load("floor1window1.png", Texture.class);
					assetManager.load("floor2window0.png", Texture.class);
					assetManager.load("floor2window1.png", Texture.class);
					assetManager.load("window00.png", Texture.class);
					assetManager.load("window01.png", Texture.class);
					assetManager.load("window10.png", Texture.class);
					assetManager.load("window11.png", Texture.class);
					assetManager.load("obstacle0.png", Texture.class);
					assetManager.load("obstacle1.png", Texture.class);
					assetManager.load("obstacle2.png", Texture.class);
					assetManager.load("obstacle3.png", Texture.class);
					assetManager.load("obstacle4.png", Texture.class);
					assetManager.load("obstacle5.png", Texture.class);
					assetManager.load("obstacle6.png", Texture.class);
					assetManager.load("obstacle7.png", Texture.class);
					assetManager.load("obstacle8.png", Texture.class);
					assetManager.load("obstacle9.png", Texture.class);
					assetManager.load("obstacle10.png", Texture.class);
					assetManager.load("obstacle11.png", Texture.class);
					assetManager.load("obstacle12.png", Texture.class);
					assetManager.load("obstacle13.png", Texture.class);
					assetManager.load("obstacle14.png", Texture.class);
					assetManager.load("obstacle15.png", Texture.class);
					assetManager.load("obstacle16.png", Texture.class);
					assetManager.load("obstacle17.png", Texture.class);
					assetManager.load("city0.jpg", Texture.class);
					assetManager.load("city1.jpg", Texture.class);
					assetManager.load("city2.jpg", Texture.class);
					assetManager.load("city3.jpg", Texture.class);
					assetManager.load("city4.jpg", Texture.class);
					assetManager.load("city5.jpg", Texture.class);
					assetManager.load("city6.jpg", Texture.class);
					assetManager.load("city7.jpg", Texture.class);
					assetManager.load("city8.jpg", Texture.class);
					assetManager.load("city9.jpg", Texture.class);
					assetManager.load("city10.jpg", Texture.class);
					assetManager.load("city11.jpg", Texture.class);
					assetManager.load("city12.jpg", Texture.class);
					assetManager.load("city13.jpg", Texture.class);
					assetManager.load("city14.jpg", Texture.class);
					assetManager.load("obstacle00.png", Texture.class);
					assetManager.load("obstacle01.png", Texture.class);
					assetManager.load("obstacle02.png", Texture.class);
					assetManager.load("obstacle03.png", Texture.class);
					assetManager.load("obstacle04.png", Texture.class);
					assetManager.load("needle.png", Texture.class);
					assetManager.load("play.png", Texture.class);
					assetManager.load("pause.png", Texture.class);
					assetManager.load("restart.png", Texture.class);
					assetManager.load("home.png", Texture.class);
					assetManager.load("plane_sound.wav", Sound.class);
					assetManager.load("plane_sound_intense.wav", Sound.class);
					assetManager.load("rocket_sound.wav", Sound.class);
					assetManager.load("hit.wav", Sound.class);
					assetManager.load("blood.wav", Sound.class);
					assetManager.load("boost.png", Texture.class);
					assetManager.load("powerup.wav", Sound.class);
					assetManager.load("flame.png", Texture.class);
					assetManager.load("bark.mp3", Sound.class);
					assetManager.load("meow.mp3", Sound.class);
					assetManager.load("jump.wav", Sound.class);
					assetManager.load("bite.mp3", Sound.class);
					assetManager.load("0.png", Texture.class);
					assetManager.load("1.png", Texture.class);
					assetManager.load("-1.png", Texture.class);
					assetManager.load("2.png", Texture.class);
					assetManager.load("-2.png", Texture.class);
					assetManager.load("3.png", Texture.class);
					assetManager.load("-3.png", Texture.class);
					assetManager.load("4.png", Texture.class);
					assetManager.load("-4.png", Texture.class);
					assetManager.load("5.png", Texture.class);
					assetManager.load("-5.png", Texture.class);
					
					for(int i=0;i<30;i++)
						assetManager.load("whiteFan/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
					for(int i=0;i<30;i++)
						assetManager.load("blackFan/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
					for(int i=0;i<31;i++)
						assetManager.load("human0/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
					for(int i=0;i<31;i++)
									assetManager.load("human01/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
					for(int i=0;i<31;i++)
									assetManager.load("human02/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
					for(int i=0;i<31;i++)
									assetManager.load("human03/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
					for(int i=0;i<31;i++)
									assetManager.load("human00/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
					for(int i=0;i<31;i++)
									assetManager.load("human000/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
					for(int i=0;i<61;i++)
									assetManager.load("catRun/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
					for(int i=0;i<61;i++)
									assetManager.load("dogRun/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
					for(int i=0;i<31;i++)
									assetManager.load("catIdle/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
					for(int i=0;i<31;i++)
									assetManager.load("dogIdle/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class);
					}
		}
		
		/** @see ApplicationListener#resize(int, int) */
		public void resize (int width, int height){}

		/** @see ApplicationListener#pause() */
		public void pause (){}

		/** @see ApplicationListener#resume() */
		public void resume (){}

		/** Called when this screen is no longer the current screen for a {@link Game}. */
		public void hide (){}

		public void dispose(){}
	}
	
	private class MainMenu implements Screen{
		
		Game game;
		Stage mainMenu;
		Stage customization;
		Stage settings;
		Stage rules;
		Animation<TextureRegion> beachAnim;
		float noodle0Speed = 0.25f;
		Image noodle0;
		Image skin0;
		Stage currentStage;
		Image background;
		float scrollPaneHeight;
		
		public MainMenu(final Game game){
			this.game = game;
			mainMenu = new Stage(screenViewport);
			customization = new Stage(screenViewport);
			settings = new Stage(screenViewport);
			rules = new Stage(screenViewport);
						
					Image NOODLE = new Image(new TextureRegionDrawable(assetManager.get("logo_text.png", Texture.class)));
					TextButtonDown continueGame = new TextButtonDown("PLAY", textButtonStyle);
					TextButtonDown newGame = new TextButtonDown("EXIT", textButtonStyle);
					ImageButtonDown settingsDown = new ImageButtonDown("settings.png");
					noodle0 = new Image(assetManager.get("noodle0.bmp", Texture.class));
					skin0 = new Image(assetManager.get("skin0.bmp", Texture.class));
					skin0.setTouchable(Touchable.disabled);
					final ImageButtonDown closeDown = new ImageButtonDown("close.png");
						Animation<TextureRegion> mManAnim = new Animation<TextureRegion>(0.017f, mManFrames);
						mManAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
						final Image mMan = new AnimatedImage(mManAnim);
						Animation<TextureRegion> mPetAnim = new Animation<TextureRegion>(0.017f, mPetFrames);
						mPetAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
						final Image mPet = new AnimatedImage(mPetAnim);
					Image mWall0 = new Image(assetManager.get("wall"+String.valueOf(mWallIndex)+".jpg", Texture.class));
					Image mWall1 = new Image(assetManager.get("wall"+String.valueOf(mWallIndex)+".jpg", Texture.class));
					Image mFloor0 = new Image(assetManager.get("floor"+String.valueOf(mFloorIndex)+".png", Texture.class));
					Image mFloor1 = new Image(assetManager.get("floor"+String.valueOf(mFloorIndex)+".png", Texture.class));
					//Animation<TextureRegion> beachAnim = new Animation<TextureRegion>(0.05f, beachFrames);
					//beachAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
					//background = new Image(assetManager.get("city"+String.valueOf(MathUtils.random.nextInt(7))+".jpg", Texture.class));
					
					int NOODLEHeight = Gdx.graphics.getHeight()/2;
					
					//background.setSize(Gdx.graphics.getHeight()*background.getWidth()/background.getHeight(), Gdx.graphics.getHeight());
					NOODLE.setSize(NOODLEHeight*assetManager.get("logo_text.png", Texture.class).getWidth()/assetManager.get("logo_text.png", Texture.class).getHeight(), NOODLEHeight);
					continueGame.setSize(buttonTextWidth, buttonTextHeight);
					newGame.setSize(buttonTextWidth, buttonTextHeight);
					settingsDown.setSize(buttonImageWidth, buttonImageWidth);
					noodle0.setSize(buttonImageWidth, buttonImageWidth*assetManager.get("noodle0.bmp", Texture.class).getHeight()/assetManager.get("noodle0.bmp", Texture.class).getWidth());
					noodle0.setOrigin(noodle0.getWidth()/2, noodle0.getHeight()/2);
					skin0.setSize(noodle0.getWidth(), noodle0.getHeight());
					skin0.setOrigin(noodle0.getWidth()/2, noodle0.getHeight()/2);
					closeDown.setSize(buttonImageWidth, buttonImageWidth);
					mMan.setSize(mMan.getWidth()*0.80f*Gdx.graphics.getHeight()/1080,
							mMan.getHeight()*0.80f*Gdx.graphics.getHeight()/1080);
					if(!mManWhite)
							mMan.setSize(mMan.getWidth()*1.33f,
											mMan.getHeight()*1.33f);
					mPet.setSize(mPet.getWidth()*0.75f*Gdx.graphics.getHeight()/1080,
								mPet.getHeight()*0.75f*Gdx.graphics.getHeight()/1080);
						if(mPetCat)
							mPet.setSize(mPet.getWidth()*0.5f,
											mPet.getHeight()*0.5f);
					mWall0.setSize(Gdx.graphics.getHeight()*mWall0.getWidth()/mWall0.getHeight(),
							Gdx.graphics.getHeight());
					mWall1.setSize(Gdx.graphics.getHeight()*mWall1.getWidth()/mWall1.getHeight(),
							Gdx.graphics.getHeight());
					mFloor0.setSize(Gdx.graphics.getHeight()*mFloor0.getWidth()/mFloor0.getHeight(),
							Gdx.graphics.getHeight());
					mFloor1.setSize(Gdx.graphics.getHeight()*mFloor1.getWidth()/mFloor1.getHeight(),
							Gdx.graphics.getHeight());
									
					//mainMenu.addActor(background);
					//background.setPosition(0, 0);
					mainMenu.addActor(mWall0);
					mWall0.setPosition(0, 0);
					mainMenu.addActor(mWall1);
					mWall1.setPosition(mWall0.getWidth(), 0);
					mainMenu.addActor(mFloor0);
					mFloor0.setPosition(0, 0);
					mainMenu.addActor(mFloor1);
					mFloor1.setPosition(mFloor0.getWidth(), 0);
					mainMenu.addActor(mPet);
					mainMenu.addActor(mMan);
					mainMenu.addActor(NOODLE);
					NOODLE.setPosition(Gdx.graphics.getWidth()/2-NOODLE.getWidth()/2, Gdx.graphics.getHeight()-NOODLEHeight);
					mainMenu.addActor(continueGame);
					continueGame.setPosition(Gdx.graphics.getWidth()/2-buttonTextWidth/2, Gdx.graphics.getHeight()-NOODLEHeight-buttonTextHeight);
					mainMenu.addActor(newGame);
					newGame.setPosition(Gdx.graphics.getWidth()/2-buttonTextWidth/2, Gdx.graphics.getHeight()-NOODLEHeight-buttonTextHeight*2);
					mainMenu.addActor(settingsDown);
					settingsDown.setPosition(Gdx.graphics.getWidth()-buttonImageWidth, 0);
					mainMenu.addActor(noodle0);
					noodle0.setPosition(noodle0.getWidth()/20, noodle0.getHeight()/4);
					mainMenu.addActor(skin0);
					skin0.setPosition(noodle0.getX(), noodle0.getY());
					mPet.setPosition(continueGame.getX()-mPet.getWidth()*4f/5f, 0);
					mMan.setPosition(settingsDown.getX()-mMan.getWidth(), 0);
					
					Image blackTransparentScreen = new Image(assetManager.get("black_transparent_screen.png", Texture.class));
					Image characterWindow = new Image(assetManager.get("window_no_title.png", Texture.class));
					TextButtonDown equipDown = new TextButtonDown("EQUIP", textButtonStyle);
					Label.LabelStyle SKINStyle = new Label.LabelStyle();
					SKINStyle.font = daySansMedium;
					Label SKIN = new Label("SELECT NOODLE", SKINStyle);
					SKIN.setAlignment(Align.center);
					Table table = new Table();
					ScrollPane scrollPane = new ScrollPane(table);
					final Image[] skins = new Image[6];
					for(int i=0;i<skins.length;i++)
					{
						final int iFinal = i;
						skins[i] = new Image(assetManager.get("skin"+String.valueOf(iFinal)+".bmp", Texture.class));
						skins[i].addListener(new ClickListener(){
							@Override
							public void clicked(InputEvent event, float x, float y) {
								if(SOUND.isChecked())assetManager.get("select.wav", Sound.class).play(0.5f);
								for(int j=0;j<skins.length;j++)
								{
									skins[j].setDrawable(new TextureRegionDrawable(assetManager.get("skin"+String.valueOf(j)+".bmp", Texture.class)));
									skins[j].setSize(scrollPaneHeight*skins[j].getWidth()/skins[j].getHeight(), scrollPaneHeight);
								}
								skinSelected = iFinal;
								skin0.setDrawable(skins[skinSelected].getDrawable());
								skins[skinSelected].setDrawable(new TextureRegionDrawable(assetManager.get("skin"+String.valueOf(iFinal)+"_selected.bmp", Texture.class)));
								skins[skinSelected].setSize(scrollPaneHeight*skins[skinSelected].getWidth()/skins[skinSelected].getHeight(), scrollPaneHeight);
							}
						});
					}
					
					Table table0 = new Table();
					ScrollPane scrollPane0 = new ScrollPane(table0);
					final Image[] noodles = new Image[8];
					for(int i=0;i<noodles.length;i++)
					{
						final int iFinal = i;
						noodles[i] = new Image(assetManager.get("noodle"+String.valueOf(iFinal)+".bmp", Texture.class));
						noodles[i].addListener(new ClickListener(){
							@Override
							public void clicked(InputEvent event, float x, float y) {
								if(SOUND.isChecked())assetManager.get("select.wav", Sound.class).play(0.5f);
								for(int j=0;j<noodles.length;j++)
								{
									noodles[j].setDrawable(new TextureRegionDrawable(assetManager.get("noodle"+String.valueOf(j)+".bmp", Texture.class)));
									noodles[j].setSize(scrollPaneHeight*noodles[j].getWidth()/noodles[j].getHeight(), scrollPaneHeight);
								}
								noodleSelected = iFinal;
								noodle0.setDrawable(noodles[noodleSelected].getDrawable());
								noodles[noodleSelected].setDrawable(new TextureRegionDrawable(assetManager.get("noodle"+String.valueOf(iFinal)+"_selected.bmp", Texture.class)));
								noodles[noodleSelected].setSize(scrollPaneHeight*noodles[noodleSelected].getWidth()/noodles[noodleSelected].getHeight(), scrollPaneHeight);
							}
						});
					}
					
					blackTransparentScreen.setSize(Gdx.graphics.getHeight()*blackTransparentScreen.getWidth()/blackTransparentScreen.getHeight(), Gdx.graphics.getHeight());
					characterWindow.setSize(windowNoTitleSize, windowNoTitleSize);
					equipDown.setSize(buttonTextWidth, buttonTextHeight);
					SKIN.setSize(buttonTextWidth, buttonTextHeight);
					scrollPaneHeight = (windowNoTitleSize-SKIN.getHeight()-equipDown.getHeight())/2*118/100;
					scrollPane.setSize(windowNoTitleSize*90/100, scrollPaneHeight);
					for(int i=0;i<skins.length;i++)
					{
						skins[i].setSize(scrollPaneHeight*skins[i].getWidth()/skins[i].getHeight(), scrollPaneHeight);
						table.add(skins[i]).width(skins[i].getWidth()).height(skins[i].getHeight());
					}
					scrollPane0.setSize(windowNoTitleSize*90/100, scrollPaneHeight);
					for(int i=0;i<noodles.length;i++)
					{
						noodles[i].setSize(scrollPaneHeight*noodles[i].getWidth()/noodles[i].getHeight(), scrollPaneHeight);
						table0.add(noodles[i]).width(noodles[i].getWidth()).height(noodles[i].getHeight());
					}
					
					customization.addActor(blackTransparentScreen);
					blackTransparentScreen.setPosition(0, 0);
					customization.addActor(characterWindow);
					characterWindow.setPosition(Gdx.graphics.getWidth()/2-windowNoTitleSize/2, windowNoTitleSize*25/100/2);
					customization.addActor(SKIN);
					SKIN.setPosition(characterWindow.getX()+windowNoTitleSize/2-buttonTextWidth/2, characterWindow.getY()+windowNoTitleSize-buttonTextHeight);
					customization.addActor(scrollPane);
					scrollPane.setPosition(characterWindow.getX()+(windowNoTitleSize-scrollPane.getWidth())/2, characterWindow.getY()+equipDown.getHeight());
					customization.addActor(equipDown);
					equipDown.setPosition(characterWindow.getX()+windowNoTitleSize/2-buttonTextWidth/2, characterWindow.getY());
					customization.addActor(scrollPane0);
					scrollPane0.setPosition(characterWindow.getX()+(windowNoTitleSize-scrollPane0.getWidth())/2, characterWindow.getY()+equipDown.getHeight()+scrollPane.getHeight());
					customization.addActor(closeDown);
					closeDown.setPosition(characterWindow.getX()+windowNoTitleSize-buttonImageWidth/2, characterWindow.getY()+windowNoTitleSize-buttonImageWidth*75/100);
					
					equipDown.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y) {
							skinIndex = skinSelected;
							noodleIndex = noodleSelected;
							
							InputEvent event1 = new InputEvent();
							event1.setType(InputEvent.Type.touchDown);
							closeDown.fire(event1);

							InputEvent event2 = new InputEvent();
							event2.setType(InputEvent.Type.touchUp);
							closeDown.fire(event2);
						}
					});
					
					closeDown.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y) {
							
								for(int j=0;j<skins.length;j++)
								{
									skins[j].setDrawable(new TextureRegionDrawable(assetManager.get("skin"+String.valueOf(j)+".bmp", Texture.class)));
									skins[j].setSize(scrollPaneHeight*skins[j].getWidth()/skins[j].getHeight(), scrollPaneHeight);
								}
								for(int j=0;j<noodles.length;j++)
								{
									noodles[j].setDrawable(new TextureRegionDrawable(assetManager.get("noodle"+String.valueOf(j)+".bmp", Texture.class)));
									noodles[j].setSize(scrollPaneHeight*noodles[j].getWidth()/noodles[j].getHeight(), scrollPaneHeight);
								}
							
							skin0.setDrawable(skins[skinIndex].getDrawable());
							noodle0.setDrawable(noodles[noodleIndex].getDrawable());
							currentStage = null;
							((AnimatedImage)mMan).animation.setFrameDuration(0.034f);
							((AnimatedImage)mPet).animation.setFrameDuration(0.034f);
							Gdx.input.setInputProcessor(mainMenu);
						}
					});
					
					Image blackTransparentScreenFull = new Image(assetManager.get("black_transparent_screen_full.png", Texture.class));
					Image settingsWindow = new Image(assetManager.get("window_no_title.png", Texture.class));
					Label.LabelStyle SETTINGSStyle = new Label.LabelStyle();
					SETTINGSStyle.font = daySansLarge;
					Label SETTINGS = new Label("SETTINGS", SETTINGSStyle);
					final ImageButtonDown closeDownS = new ImageButtonDown("close.png");
					CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle(new TextureRegionDrawable(assetManager.get("off.png", Texture.class)), 
																			new TextureRegionDrawable(assetManager.get("on.png", Texture.class)), daySansMedium, Color.WHITE);
					SOUND = new CheckBox("SOUND EFFECTS", checkBoxStyle);
					TILT = new CheckBox("TILT CONTROLS", checkBoxStyle);
					SOUND.setChecked(true);
					TILT.setChecked(true);
					
					blackTransparentScreenFull.setSize(Gdx.graphics.getHeight()*blackTransparentScreenFull.getWidth()/blackTransparentScreenFull.getHeight(), Gdx.graphics.getHeight());
					settingsWindow.setSize(windowNoTitleSize, windowNoTitleSize);
					closeDownS.setSize(buttonImageWidth, buttonImageWidth);
					SOUND.getImage().setScaling(Scaling.fill);
					SOUND.getImageCell().size(buttonTextHeight/2);
					SOUND.left().pad(0);
					SOUND.getLabelCell().left().pad(buttonTextHeight/4);
					TILT.getImage().setScaling(Scaling.fill);
					TILT.getImageCell().size(buttonTextHeight/2);
					TILT.left().pad(0);
					TILT.getLabelCell().left().pad(buttonTextHeight/4);
					
					settings.addActor(blackTransparentScreenFull);
					blackTransparentScreenFull.setPosition(0, 0);
					settings.addActor(settingsWindow);
					settingsWindow.setPosition(Gdx.graphics.getWidth()/2-windowNoTitleSize/2, windowNoTitleSize*25/100/2);
					settings.addActor(SETTINGS);
					SETTINGS.setPosition(settingsWindow.getX()+windowNoTitleSize/2-SETTINGS.getWidth()/2, settingsWindow.getY()+windowNoTitleSize-SETTINGS.getHeight()-windowNoTitlePadding);
					settings.addActor(SOUND);
					SOUND.setPosition(settingsWindow.getX()+windowNoTitlePadding, SETTINGS.getY()-buttonTextHeight*4/3);
					settings.addActor(TILT);
					TILT.setPosition(settingsWindow.getX()+windowNoTitlePadding, SOUND.getY()-buttonTextHeight);
					settings.addActor(closeDownS);
					closeDownS.setPosition(settingsWindow.getX()+windowNoTitleSize-buttonImageWidth/2, settingsWindow.getY()+windowNoTitleSize-buttonImageWidth*75/100);

					Label.LabelStyle RULESStyle = new Label.LabelStyle();
					RULESStyle.font = daySansMedium;
					Label RULES = new Label("RULES", RULESStyle);
					RULES.setAlignment(Align.center);
					Label.LabelStyle rulesStyle = new Label.LabelStyle();
					rulesStyle.font = daySansSmall;
					Label rulesTxt = new Label("Mission Objective: Prevent Painful Injections – Save the Smiling Man!\n\nThe smiling man is at risk every time a red patch appears on his skin. These red patches indicate the need for an injection, and without intervention, the injection will be painfully administered. Noodle is his only hope for a pain-free solution. Your critical mission is to ensure Noodle reaches the red patch before the syringe does.\n\nMission Brief:\n\nIdentify the Target: As soon as a red patch appears on the smiling man's skin, it’s your signal to act.\n\nDeploy Noodle: Guide Noodle with precision towards the red patch. Speed is of the essence, but avoid any obstacles that could delay Noodle's mission.\n\nOutpace the Syringe: Ensure Noodle reaches the red patch first to neutralize the threat of pain.\n\nMission Success:\n\nComplete your mission by navigating Noodle to the red patch before the syringe, ensuring a pain-free injection for the smiling man. Your quick thinking and agile maneuvering are crucial to his comfort and well-being. Keep honing your skills to stay ahead in this high-stakes game!", rulesStyle);
					ScrollPane rulesPane = new ScrollPane(rulesTxt);
					Image rulesWindow = new Image(assetManager.get("window_no_title.png", Texture.class));
					TextButtonDown okDown = new TextButtonDown("OK", textButtonStyle);

					rulesWindow.setSize(windowNoTitleSize, windowNoTitleSize);
					RULES.setSize(buttonTextWidth, buttonTextHeight);
					okDown.setSize(buttonTextWidth, buttonTextHeight);
					rulesTxt.setWidth(windowNoTitleSize*90/100);
					rulesTxt.setWrap(true);
					rulesPane.setSize(windowNoTitleSize*90/100, windowNoTitleSize-RULES.getHeight()-okDown.getHeight());

					rules.addActor(blackTransparentScreenFull);
					rules.addActor(rulesWindow);
					rulesWindow.setPosition(Gdx.graphics.getWidth()/2-windowNoTitleSize/2, windowNoTitleSize*25/100/2);
					rules.addActor(RULES);
					RULES.setPosition(rulesWindow.getX()+windowNoTitleSize/2-buttonTextWidth/2, rulesWindow.getY()+windowNoTitleSize-buttonTextHeight);
					rules.addActor(rulesPane);
					rulesPane.setPosition(rulesWindow.getX()+(windowNoTitleSize-rulesPane.getWidth())/2, rulesWindow.getY()+okDown.getHeight());
					rules.addActor(okDown);
					okDown.setPosition(rulesWindow.getX()+windowNoTitleSize/2-buttonTextWidth/2, rulesWindow.getY());
					okDown.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y)
						{
							loadingGameScreen = new LoadingGame(game);
							setScreen(loadingGameScreen);
						}
					});

					SOUND.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y)
						{
							if(SOUND.isChecked())assetManager.get("click.wav", Sound.class).play(0.5f);
						}
					});
					
					TILT.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y)
						{
							if(SOUND.isChecked())assetManager.get("click.wav", Sound.class).play(0.5f);
						}
					});
					
					continueGame.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y)
						{
							//if(!username.equals(""))
								if(loadingGameScreen==null)
								{
									currentStage = rules;
									Gdx.input.setInputProcessor(rules);
								}else
								{
									gameScreen = new GameScreen(game);
									setScreen(gameScreen);
								}
							//else
								//getUserName();
						}
					});
					newGame.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y)
						{
							Gdx.app.exit();
							Gdx.net.openURI("https://painless.world");
						}
					});
					noodle0.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y) {
							if(SOUND.isChecked())assetManager.get("click.wav", Sound.class).play(0.5f);
							currentStage = customization;
							skins[skinIndex].setDrawable(new TextureRegionDrawable(assetManager.get("skin"+String.valueOf(skinIndex)+"_selected.bmp", Texture.class)));
								skins[skinIndex].setSize(scrollPaneHeight*skins[skinIndex].getWidth()/skins[skinIndex].getHeight(), scrollPaneHeight);
							noodles[noodleIndex].setDrawable(new TextureRegionDrawable(assetManager.get("noodle"+String.valueOf(noodleIndex)+"_selected.bmp", Texture.class)));
								noodles[noodleIndex].setSize(scrollPaneHeight*noodles[noodleIndex].getWidth()/noodles[noodleIndex].getHeight(), scrollPaneHeight);
							Gdx.input.setInputProcessor(customization);
						}
					});
					settingsDown.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y) {
							currentStage = settings;
							Gdx.input.setInputProcessor(currentStage);
						}
					});
					closeDownS.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y) {
							((AnimatedImage)mMan).animation.setFrameDuration(0.034f);
							((AnimatedImage)mPet).animation.setFrameDuration(0.034f);
							currentStage = null;
							Gdx.input.setInputProcessor(mainMenu);
						}
					});
		}
		
		public void render(float delta){
			if(noodle0.getRotation() >= 15)
				noodle0Speed*=-1;
			else
				if(noodle0.getRotation() <= -15)
					noodle0Speed*=-1;
			noodle0.setRotation(noodle0.getRotation()+noodle0Speed);
			skin0.setRotation(noodle0.getRotation()+noodle0Speed);
			
			mainMenu.act(Gdx.graphics.getDeltaTime());
			mainMenu.draw();
			if(currentStage!=null)
			{
				currentStage.act(Gdx.graphics.getDeltaTime());
				currentStage.draw();
			}
			assetManager.update();
		}
		
		public void show ()
		{
			currentStage = null;
			Gdx.input.setInputProcessor(mainMenu);
		}
		
		/** @see ApplicationListener#resize(int, int) */
		public void resize (int width, int height){}

		/** @see ApplicationListener#pause() */
		public void pause (){}

		/** @see ApplicationListener#resume() */
		public void resume (){}

		/** Called when this screen is no longer the current screen for a {@link Game}. */
		public void hide (){}

		public void dispose(){}
	}
	
		private void getUserName()
		{
						/*Gdx.input.getTextInput(new Input.TextInputListener() {
						   @Override
						   public void input (String text) {
							   username = text;
								final String userName = username;
								new Thread() {
									public void run() {
										FTPClient ftpClient = new FTPClient();
										try {
											ftpClient.connect(server, port);
											ftpClient.login(user, pass);
											//ftpClient.enterLocalPassiveMode();
											ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
											
											String secondRemoteFile = "/public_html/"+userName;
											InputStream inputStream = ftpClient.retrieveFileStream(secondRemoteFile);
											//System.out.println(ftpClient.getReplyCode());
											if(!(inputStream==null))
											{
												HScore = Integer.parseInt(readFromStream(inputStream).replace("\n", ""));
												inputStream.close();
											}
											ftpClient.completePendingCommand();
											String firstRemoteFile = "/public_html/"+"highest";
											InputStream inputStream2 = ftpClient.retrieveFileStream(firstRemoteFile);
											//System.out.println(ftpClient.getReplyCode());
												String userName = readFromStream(inputStream2).replace("\n", "");
												inputStream2.close();
											ftpClient.completePendingCommand();
											String thirdRemoteFile = "/public_html/"+userName;
											InputStream inputStream1 = ftpClient.retrieveFileStream(thirdRemoteFile);
											//System.out.println(ftpClient.getReplyCode());
											
												serverHighest = Integer.parseInt(readFromStream(inputStream1).replace("\n", ""));
												inputStream1.close();
										} catch (IOException ex) {
										} finally {
											try {
												if (ftpClient.isConnected()) {
													ftpClient.logout();
													ftpClient.disconnect();
												}
											} catch (IOException ex){}
										}
									}
								}.start();
						   }

						   @Override
						   public void canceled () {
							   Gdx.app.exit();
						   }
						}, "Username", "", "create or enter existing");*/
		}

	private class LoadingGame implements Screen{
		
		Stage loading;
		Game game;
		Image[] backgrounds;
		Label LOADING;
		ProgressBar loadingBar;
		Label TAP;
		
		public LoadingGame(Game game)
		{
			this.game = game;
			
			loading = new Stage(screenViewport);
			
			Label.LabelStyle loadingStyle = new Label.LabelStyle();
			loadingStyle.font = daySansSmall;			
			Label.LabelStyle tapStyle = new Label.LabelStyle();
			tapStyle.font = daySansMedium;			
			
			ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
			Pixmap pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
			pixmap.setColor(Color.BLACK);
			pixmap.fill();
			TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
			pixmap.dispose();
			progressBarStyle.background = drawable;
			pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
			pixmap.setColor(Color.BLUE);
			pixmap.fill();
			drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
			pixmap.dispose();
			progressBarStyle.knobBefore = drawable;
			/*pixmap = new Pixmap(0, 20, Pixmap.Format.RGBA8888);
			pixmap.setColor(Color.GREEN);
			pixmap.fill();
			drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
			pixmap.dispose();
			progressBarStyle.knob = drawable;*/
			
			LOADING = new Label("LOADING 0%", loadingStyle);
			TAP = new Label("TAP TO START", tapStyle);
			TAP.setTouchable(Touchable.disabled);
			backgrounds = new Image[1];
			for(int i=0;i<backgrounds.length;i++)
				backgrounds[i] = new Image(new TextureRegionDrawable(assetManager.get("loading"+String.valueOf(i)+".png", Texture.class)));
			loadingBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);
			loadingBar.setValue(0f);
			loadingBar.setAnimateDuration(0.5f);
			
			for(int i=0;i<backgrounds.length;i++)
				backgrounds[i].setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			loadingBar.setSize(Gdx.graphics.getWidth()*9f/10f, Gdx.graphics.getHeight()/100f);
			
			int i = MathUtils.random.nextInt(backgrounds.length);
			loading.addActor(backgrounds[i]);
			loading.addActor(LOADING);
			loading.addActor(loadingBar);
			
			backgrounds[i].setPosition(0, 0);
			loadingBar.setPosition(Gdx.graphics.getWidth()*5f/100f, Gdx.graphics.getHeight()/100f);
			LOADING.setPosition(loadingBar.getX(), loadingBar.getY()+loadingBar.getHeight());
		}
		
		/** Called when this screen becomes the current screen for a {@link Game}. */
		public void show ()
		{
			Gdx.input.setInputProcessor(loading);
		}

		/** Called when the screen should render itself.
		 * @param delta The time in seconds since the last render. */
		public void render (float delta)
		{
			loading.act(delta);
			loading.draw();
			
			loadingBar.setValue(assetManager.getProgress());
			LOADING.setText("LOADING "+String.valueOf((int)(assetManager.getProgress()*100f))+"%");
			
			if(whiteFanFrames==null && assetManager.update())
			{
				whiteFanFrames = new TextureRegion[30];
						for(int i=0;i<30;i++)
							whiteFanFrames[i] = new TextureRegion(assetManager.get("whiteFan/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
						blackFanFrames = new TextureRegion[30];
						for(int i=0;i<30;i++)
							blackFanFrames[i] = new TextureRegion(assetManager.get("blackFan/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
						human0Frames = new TextureRegion[31];
						for(int i=0;i<31;i++)
							human0Frames[i] = new TextureRegion(assetManager.get("human0/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
						human01Frames = new TextureRegion[31];
						for(int i=0;i<31;i++)
							human01Frames[i] = new TextureRegion(assetManager.get("human01/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
						human02Frames = new TextureRegion[31];
						for(int i=0;i<31;i++)
							human02Frames[i] = new TextureRegion(assetManager.get("human02/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
						human03Frames = new TextureRegion[31];
						for(int i=0;i<31;i++)
							human03Frames[i] = new TextureRegion(assetManager.get("human03/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
						human00Frames = new TextureRegion[31];
						for(int i=0;i<31;i++)
							human00Frames[i] = new TextureRegion(assetManager.get("human00/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
						human000Frames = new TextureRegion[31];
						for(int i=0;i<31;i++)
							human000Frames[i] = new TextureRegion(assetManager.get("human000/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
						human1Frames = human0Frames;
						human10Frames = human00Frames;
						human100Frames = human000Frames;
						human11Frames = human01Frames;
						human12Frames = human02Frames;
						human13Frames = human03Frames;
						catRunFrames = new TextureRegion[61];
						for(int i=0;i<61;i++)
						{
							catRunFrames[i] = new TextureRegion(assetManager.get("catRun/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
							catRunFrames[i].flip(true, false);
						}
						dogRunFrames = new TextureRegion[61];
						for(int i=0;i<61;i++)
							dogRunFrames[i] = new TextureRegion(assetManager.get("dogRun/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
						catIdleFrames = new TextureRegion[31];
						for(int i=0;i<31;i++)
						{
							catIdleFrames[i] = new TextureRegion(assetManager.get("catIdle/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
							catIdleFrames[i].flip(true, false);
						}
						dogIdleFrames = new TextureRegion[31];
						for(int i=0;i<31;i++)
							dogIdleFrames[i] = new TextureRegion(assetManager.get("dogIdle/"+(i+1<10?"000":(i+1<100?"00":"0"))+String.valueOf(i+1)+"_2"+".png", Texture.class));
						
				loadingBar.remove();
				LOADING.remove();
				loading.addActor(TAP);
				TAP.setPosition(Gdx.graphics.getWidth()/2-TAP.getWidth()/2, Gdx.graphics.getHeight()/100f);
			}
			if(assetManager.update() && Gdx.input.justTouched())
			{
					if(SOUND.isChecked())assetManager.get("click.wav", Sound.class).play(0.5f);
								gameScreen = new GameScreen(game);
								setScreen(gameScreen);
			}
		}

		/** @see ApplicationListener#resize(int, int) */
		public void resize (int width, int height){}

		/** @see ApplicationListener#pause() */
		public void pause (){}

		/** @see ApplicationListener#resume() */
		public void resume (){}

		/** Called when this screen is no longer the current screen for a {@link Game}. */
		public void hide (){}

		/** Called when this screen should release all resources. */
		public void dispose (){}
	}
	
	private class GameScreen implements Screen{
	
		Game game;
		Stage stage;
		Image wall0;
		float accPrev;
		float alpha = 0;
		float omega = 0;
		float omegaMax = 2;
		Image[][] backgrounds; 
		int level = 1;
		int[] currentBackground;
		int[] nextBackground;
		int levelWidth = 10;
		boolean isDoor;
		boolean isNextDoor;
		Sprite[] obstacles;
		int petIndex = 0;
		Sprite pet;
		int[] obstacleIndex;
		float[][][] obstaclesBounds;
		float[][] patchesBounds ;
		boolean[] ceiling;
		SpriteBatch spriteBatch;
		Sprite noodle;
		Sprite skin;
		float nSpeed;
		int neSpeed;
		Image city;
		ShapeRenderer shapeRenderer;
		Polygon nBounds;
		Polygon neBounds;
		Sprite needle;
		int iHuman;
		boolean patched;
		GlyphLayout HILayout;
		float score = 0f;
		int pScore = 0;
		int dScore = 0;
		int dScore1 = 0;
		int dScore2 = 0;
		Stage gameOver;
		boolean isGameOver = false;
		boolean paused = false;
		ImageButtonDown pauseDown;
		Image pause;
		Image gameOverWindow;
		ImageButtonDown restartDown;
		Image resume;
		ImageButtonDown resumeDown;
		Image restart;
		ImageButtonDown homeDown;
		Image home;
		Label SCORE;
		Label HSCORE;
		Label title;
		Sound planeSound;
		Sound planeSoundIntense;
		Sound rocketSound;
		long planeSoundId;
		long rocketSoundId;
		long planeSoundIntenseId;
		Sprite boost;
		boolean boosted;
		float boostFactor;
		Sprite flame;
		Animation<TextureRegion> petIdleAnim;
		Animation<TextureRegion> petRunAnim;
		boolean hadPet = false;
		float[][] petsBounds;
		float catWidth;
		float catHeight;
		float dogWidth;
		float dogHeight;
		Polygon petBounds;
		Sprite meter;
		
		public GameScreen(final Game game)
		{	
			accPrev = 0;
			nSpeed = (10*Gdx.graphics.getHeight())/1080f;
			neSpeed = (int)nSpeed;
			boosted = false;
			boostFactor = 1f;
			this.game = game;
			spriteBatch = new SpriteBatch();
			stage = new Stage(screenViewport, spriteBatch);
			gameOver = new Stage(screenViewport, spriteBatch);
			shapeRenderer = new ShapeRenderer();
			
			obstaclesBounds = new float[][][]{
				{{0.97333336f,0.9872449f,1.0f,0.94132656f,1.0f,0.90561223f,0.952f,0.9005102f,0.936f,0.048469387f,0.06666667f,0.035714287f,0.053333335f,0.89540815f,0.008f,0.89540815f,0.0053333333f,0.9464286f,0.026666667f,0.9897959f},
				{0.9572519f,0.95757574f,0.9908397f,0.9757576f,0.96793896f,0.9951515f,0.016793894f,0.99636364f,0.0f,0.9672727f,0.030534351f,0.95757574f,0.03206107f,0.28848484f,0.0f,0.2678788f,0.030534351f,0.2569697f,0.03206107f,0.025454545f,0.9572519f,0.021818181f,0.9541985f,0.25454545f,0.9847328f,0.2630303f,0.9816794f,0.28f,0.95267177f,0.28484848f},
				{0.10535117f,0.91256833f,0.30602008f,0.98178506f,0.5033445f,1.0f,0.71404684f,0.97632056f,0.8913044f,0.9143898f,0.87458193f,0.5938069f,0.9782609f,0.54826957f,0.9381271f,0.21493624f,0.9013378f,0.21675774f,0.89966553f,0.0f,0.11204013f,0.0f,0.10367893f,0.21493624f,0.07190636f,0.21675774f,0.033444814f,0.5464481f,0.13545151f,0.5974499f},
				{0.10535117f,0.91256833f,0.30602008f,0.98178506f,0.5033445f,1.0f,0.71404684f,0.97632056f,0.8913044f,0.9143898f,0.87458193f,0.5938069f,0.9782609f,0.54826957f,0.9381271f,0.21493624f,0.9013378f,0.21675774f,0.89966553f,0.0f,0.11204013f,0.0f,0.10367893f,0.21493624f,0.07190636f,0.21675774f,0.033444814f,0.5464481f,0.13545151f,0.5974499f},
				{0.017488075f,0.98443115f,0.98569155f,0.98682636f,0.98569155f,0.0035928143f,0.0190779f,0.0047904192f},				
				{0.06984479f,0.83054394f,0.22949003f,0.9414226f,0.50110865f,0.99790794f,0.74168515f,0.9539749f,0.9390244f,0.82217574f,0.93569845f,0.60251045f,1.0f,0.51046026f,0.9678492f,0.025104603f,0.036585364f,0.029288704f,0.0088691795f,0.54184103f,0.06984479f,0.60251045f},
				{0.06984479f,0.83054394f,0.22949003f,0.9414226f,0.50110865f,0.99790794f,0.74168515f,0.9539749f,0.9390244f,0.82217574f,0.93569845f,0.60251045f,1.0f,0.51046026f,0.9678492f,0.025104603f,0.036585364f,0.029288704f,0.0088691795f,0.54184103f,0.06984479f,0.60251045f},
				{0.06984479f,0.83054394f,0.22949003f,0.9414226f,0.50110865f,0.99790794f,0.74168515f,0.9539749f,0.9390244f,0.82217574f,0.93569845f,0.60251045f,1.0f,0.51046026f,0.9678492f,0.025104603f,0.036585364f,0.029288704f,0.0088691795f,0.54184103f,0.06984479f,0.60251045f},
				{0.10535117f,0.91256833f,0.30602008f,0.98178506f,0.5033445f,1.0f,0.71404684f,0.97632056f,0.8913044f,0.9143898f,0.87458193f,0.5938069f,0.9782609f,0.54826957f,0.9381271f,0.21493624f,0.9013378f,0.21675774f,0.89966553f,0.0f,0.11204013f,0.0f,0.10367893f,0.21493624f,0.07190636f,0.21675774f,0.033444814f,0.5464481f,0.13545151f,0.5974499f},
				{0.11090909f,0.9918033f,0.8872727f,0.9901639f,0.89272726f,0.58196723f,0.9981818f,0.5081967f,0.9709091f,0.4918033f,0.95454544f,0.4442623f,0.9381818f,0.4409836f,0.9145455f,0.004918033f,0.096363634f,0.0f,0.07454546f,0.43934426f,0.054545455f,0.44262296f,0.036363635f,0.4885246f,0.010909091f,0.5114754f,0.11090909f,0.5852459f},
				{0.49444443f,0.9984375f,0.31666666f,0.971875f,0.21111111f,0.846875f,0.20833333f,0.734375f,0.17777778f,0.7109375f,0.21666667f,0.6828125f,0.24722221f,0.5890625f,0.3472222f,0.5046875f,0.31111112f,0.4765625f,0.26666668f,0.4046875f,0.22777778f,0.2984375f,0.19444445f,0.275f,0.19166666f,0.2390625f,0.21111111f,0.2203125f,0.25555557f,0.23125f,0.34166667f,0.25625f,0.34166667f,0.1640625f,0.37777779f,0.1625f,0.37777779f,0.0515625f,0.28333333f,0.0203125f,0.40555555f,0.0171875f,0.42777777f,0.0140625f,0.4611111f,0.021875f,0.45f,0.04375f,0.45f,0.1640625f,0.47777778f,0.1609375f,0.49444443f,0.225f,0.51666665f,0.2265625f,0.525f,0.165625f,0.5555556f,0.1625f,0.5611111f,0.0453125f,0.55f,0.0203125f,0.575f,0.0140625f,0.5972222f,0.0203125f,0.71944445f,0.015625f,0.71666664f,0.0265625f,0.62222224f,0.0515625f,0.6333333f,0.1625f,0.65833336f,0.165625f,0.6638889f,0.2453125f,0.7222222f,0.2421875f,0.7638889f,0.2234375f,0.7972222f,0.2203125f,0.8194444f,0.2578125f,0.775f,0.2984375f,0.7111111f,0.4484375f,0.65555555f,0.4984375f,0.75277776f,0.565625f,0.7777778f,0.6828125f,0.8277778f,0.6984375f,0.81666666f,0.721875f,0.7888889f,0.7265625f,0.7972222f,0.86875f,0.69166666f,0.9734375f},
				{0.49444443f,0.9984375f,0.31666666f,0.971875f,0.21111111f,0.846875f,0.20833333f,0.734375f,0.17777778f,0.7109375f,0.21666667f,0.6828125f,0.24722221f,0.5890625f,0.3472222f,0.5046875f,0.31111112f,0.4765625f,0.26666668f,0.4046875f,0.22777778f,0.2984375f,0.19444445f,0.275f,0.19166666f,0.2390625f,0.21111111f,0.2203125f,0.25555557f,0.23125f,0.34166667f,0.25625f,0.34166667f,0.1640625f,0.37777779f,0.1625f,0.37777779f,0.0515625f,0.28333333f,0.0203125f,0.40555555f,0.0171875f,0.42777777f,0.0140625f,0.4611111f,0.021875f,0.45f,0.04375f,0.45f,0.1640625f,0.47777778f,0.1609375f,0.49444443f,0.225f,0.51666665f,0.2265625f,0.525f,0.165625f,0.5555556f,0.1625f,0.5611111f,0.0453125f,0.55f,0.0203125f,0.575f,0.0140625f,0.5972222f,0.0203125f,0.71944445f,0.015625f,0.71666664f,0.0265625f,0.62222224f,0.0515625f,0.6333333f,0.1625f,0.65833336f,0.165625f,0.6638889f,0.2453125f,0.7222222f,0.2421875f,0.7638889f,0.2234375f,0.7972222f,0.2203125f,0.8194444f,0.2578125f,0.775f,0.2984375f,0.7111111f,0.4484375f,0.65555555f,0.4984375f,0.75277776f,0.565625f,0.7777778f,0.6828125f,0.8277778f,0.6984375f,0.81666666f,0.721875f,0.7888889f,0.7265625f,0.7972222f,0.86875f,0.69166666f,0.9734375f},
				{0.49444443f,0.9984375f,0.31666666f,0.971875f,0.21111111f,0.846875f,0.20833333f,0.734375f,0.17777778f,0.7109375f,0.21666667f,0.6828125f,0.24722221f,0.5890625f,0.3472222f,0.5046875f,0.31111112f,0.4765625f,0.26666668f,0.4046875f,0.22777778f,0.2984375f,0.19444445f,0.275f,0.19166666f,0.2390625f,0.21111111f,0.2203125f,0.25555557f,0.23125f,0.34166667f,0.25625f,0.34166667f,0.1640625f,0.37777779f,0.1625f,0.37777779f,0.0515625f,0.28333333f,0.0203125f,0.40555555f,0.0171875f,0.42777777f,0.0140625f,0.4611111f,0.021875f,0.45f,0.04375f,0.45f,0.1640625f,0.47777778f,0.1609375f,0.49444443f,0.225f,0.51666665f,0.2265625f,0.525f,0.165625f,0.5555556f,0.1625f,0.5611111f,0.0453125f,0.55f,0.0203125f,0.575f,0.0140625f,0.5972222f,0.0203125f,0.71944445f,0.015625f,0.71666664f,0.0265625f,0.62222224f,0.0515625f,0.6333333f,0.1625f,0.65833336f,0.165625f,0.6638889f,0.2453125f,0.7222222f,0.2421875f,0.7638889f,0.2234375f,0.7972222f,0.2203125f,0.8194444f,0.2578125f,0.775f,0.2984375f,0.7111111f,0.4484375f,0.65555555f,0.4984375f,0.75277776f,0.565625f,0.7777778f,0.6828125f,0.8277778f,0.6984375f,0.81666666f,0.721875f,0.7888889f,0.7265625f,0.7972222f,0.86875f,0.69166666f,0.9734375f},
				{0.49444443f,0.9984375f,0.31666666f,0.971875f,0.21111111f,0.846875f,0.20833333f,0.734375f,0.17777778f,0.7109375f,0.21666667f,0.6828125f,0.24722221f,0.5890625f,0.3472222f,0.5046875f,0.31111112f,0.4765625f,0.26666668f,0.4046875f,0.22777778f,0.2984375f,0.19444445f,0.275f,0.19166666f,0.2390625f,0.21111111f,0.2203125f,0.25555557f,0.23125f,0.34166667f,0.25625f,0.34166667f,0.1640625f,0.37777779f,0.1625f,0.37777779f,0.0515625f,0.28333333f,0.0203125f,0.40555555f,0.0171875f,0.42777777f,0.0140625f,0.4611111f,0.021875f,0.45f,0.04375f,0.45f,0.1640625f,0.47777778f,0.1609375f,0.49444443f,0.225f,0.51666665f,0.2265625f,0.525f,0.165625f,0.5555556f,0.1625f,0.5611111f,0.0453125f,0.55f,0.0203125f,0.575f,0.0140625f,0.5972222f,0.0203125f,0.71944445f,0.015625f,0.71666664f,0.0265625f,0.62222224f,0.0515625f,0.6333333f,0.1625f,0.65833336f,0.165625f,0.6638889f,0.2453125f,0.7222222f,0.2421875f,0.7638889f,0.2234375f,0.7972222f,0.2203125f,0.8194444f,0.2578125f,0.775f,0.2984375f,0.7111111f,0.4484375f,0.65555555f,0.4984375f,0.75277776f,0.565625f,0.7777778f,0.6828125f,0.8277778f,0.6984375f,0.81666666f,0.721875f,0.7888889f,0.7265625f,0.7972222f,0.86875f,0.69166666f,0.9734375f},
				{0.49444443f,0.9984375f,0.31666666f,0.971875f,0.21111111f,0.846875f,0.20833333f,0.734375f,0.17777778f,0.7109375f,0.21666667f,0.6828125f,0.24722221f,0.5890625f,0.3472222f,0.5046875f,0.31111112f,0.4765625f,0.26666668f,0.4046875f,0.22777778f,0.2984375f,0.19444445f,0.275f,0.19166666f,0.2390625f,0.21111111f,0.2203125f,0.25555557f,0.23125f,0.34166667f,0.25625f,0.34166667f,0.1640625f,0.37777779f,0.1625f,0.37777779f,0.0515625f,0.28333333f,0.0203125f,0.40555555f,0.0171875f,0.42777777f,0.0140625f,0.4611111f,0.021875f,0.45f,0.04375f,0.45f,0.1640625f,0.47777778f,0.1609375f,0.49444443f,0.225f,0.51666665f,0.2265625f,0.525f,0.165625f,0.5555556f,0.1625f,0.5611111f,0.0453125f,0.55f,0.0203125f,0.575f,0.0140625f,0.5972222f,0.0203125f,0.71944445f,0.015625f,0.71666664f,0.0265625f,0.62222224f,0.0515625f,0.6333333f,0.1625f,0.65833336f,0.165625f,0.6638889f,0.2453125f,0.7222222f,0.2421875f,0.7638889f,0.2234375f,0.7972222f,0.2203125f,0.8194444f,0.2578125f,0.775f,0.2984375f,0.7111111f,0.4484375f,0.65555555f,0.4984375f,0.75277776f,0.565625f,0.7777778f,0.6828125f,0.8277778f,0.6984375f,0.81666666f,0.721875f,0.7888889f,0.7265625f,0.7972222f,0.86875f,0.69166666f,0.9734375f},
				{0.49444443f,0.9984375f,0.31666666f,0.971875f,0.21111111f,0.846875f,0.20833333f,0.734375f,0.17777778f,0.7109375f,0.21666667f,0.6828125f,0.24722221f,0.5890625f,0.3472222f,0.5046875f,0.31111112f,0.4765625f,0.26666668f,0.4046875f,0.22777778f,0.2984375f,0.19444445f,0.275f,0.19166666f,0.2390625f,0.21111111f,0.2203125f,0.25555557f,0.23125f,0.34166667f,0.25625f,0.34166667f,0.1640625f,0.37777779f,0.1625f,0.37777779f,0.0515625f,0.28333333f,0.0203125f,0.40555555f,0.0171875f,0.42777777f,0.0140625f,0.4611111f,0.021875f,0.45f,0.04375f,0.45f,0.1640625f,0.47777778f,0.1609375f,0.49444443f,0.225f,0.51666665f,0.2265625f,0.525f,0.165625f,0.5555556f,0.1625f,0.5611111f,0.0453125f,0.55f,0.0203125f,0.575f,0.0140625f,0.5972222f,0.0203125f,0.71944445f,0.015625f,0.71666664f,0.0265625f,0.62222224f,0.0515625f,0.6333333f,0.1625f,0.65833336f,0.165625f,0.6638889f,0.2453125f,0.7222222f,0.2421875f,0.7638889f,0.2234375f,0.7972222f,0.2203125f,0.8194444f,0.2578125f,0.775f,0.2984375f,0.7111111f,0.4484375f,0.65555555f,0.4984375f,0.75277776f,0.565625f,0.7777778f,0.6828125f,0.8277778f,0.6984375f,0.81666666f,0.721875f,0.7888889f,0.7265625f,0.7972222f,0.86875f,0.69166666f,0.9734375f},
				{0.49444443f,0.9984375f,0.31666666f,0.971875f,0.21111111f,0.846875f,0.20833333f,0.734375f,0.17777778f,0.7109375f,0.21666667f,0.6828125f,0.24722221f,0.5890625f,0.3472222f,0.5046875f,0.31111112f,0.4765625f,0.26666668f,0.4046875f,0.22777778f,0.2984375f,0.19444445f,0.275f,0.19166666f,0.2390625f,0.21111111f,0.2203125f,0.25555557f,0.23125f,0.34166667f,0.25625f,0.34166667f,0.1640625f,0.37777779f,0.1625f,0.37777779f,0.0515625f,0.28333333f,0.0203125f,0.40555555f,0.0171875f,0.42777777f,0.0140625f,0.4611111f,0.021875f,0.45f,0.04375f,0.45f,0.1640625f,0.47777778f,0.1609375f,0.49444443f,0.225f,0.51666665f,0.2265625f,0.525f,0.165625f,0.5555556f,0.1625f,0.5611111f,0.0453125f,0.55f,0.0203125f,0.575f,0.0140625f,0.5972222f,0.0203125f,0.71944445f,0.015625f,0.71666664f,0.0265625f,0.62222224f,0.0515625f,0.6333333f,0.1625f,0.65833336f,0.165625f,0.6638889f,0.2453125f,0.7222222f,0.2421875f,0.7638889f,0.2234375f,0.7972222f,0.2203125f,0.8194444f,0.2578125f,0.775f,0.2984375f,0.7111111f,0.4484375f,0.65555555f,0.4984375f,0.75277776f,0.565625f,0.7777778f,0.6828125f,0.8277778f,0.6984375f,0.81666666f,0.721875f,0.7888889f,0.7265625f,0.7972222f,0.86875f,0.69166666f,0.9734375f},
				{0.49444443f,0.9984375f,0.31666666f,0.971875f,0.21111111f,0.846875f,0.20833333f,0.734375f,0.17777778f,0.7109375f,0.21666667f,0.6828125f,0.24722221f,0.5890625f,0.3472222f,0.5046875f,0.31111112f,0.4765625f,0.26666668f,0.4046875f,0.22777778f,0.2984375f,0.19444445f,0.275f,0.19166666f,0.2390625f,0.21111111f,0.2203125f,0.25555557f,0.23125f,0.34166667f,0.25625f,0.34166667f,0.1640625f,0.37777779f,0.1625f,0.37777779f,0.0515625f,0.28333333f,0.0203125f,0.40555555f,0.0171875f,0.42777777f,0.0140625f,0.4611111f,0.021875f,0.45f,0.04375f,0.45f,0.1640625f,0.47777778f,0.1609375f,0.49444443f,0.225f,0.51666665f,0.2265625f,0.525f,0.165625f,0.5555556f,0.1625f,0.5611111f,0.0453125f,0.55f,0.0203125f,0.575f,0.0140625f,0.5972222f,0.0203125f,0.71944445f,0.015625f,0.71666664f,0.0265625f,0.62222224f,0.0515625f,0.6333333f,0.1625f,0.65833336f,0.165625f,0.6638889f,0.2453125f,0.7222222f,0.2421875f,0.7638889f,0.2234375f,0.7972222f,0.2203125f,0.8194444f,0.2578125f,0.775f,0.2984375f,0.7111111f,0.4484375f,0.65555555f,0.4984375f,0.75277776f,0.565625f,0.7777778f,0.6828125f,0.8277778f,0.6984375f,0.81666666f,0.721875f,0.7888889f,0.7265625f,0.7972222f,0.86875f,0.69166666f,0.9734375f}},
				{{0.41841003f,1.0f,0.5676429f,0.99444443f,0.54114366f,0.84444445f,0.50488144f,0.8f,0.50488144f,0.47777778f,0.5620642f,0.48333332f,0.56345886f,0.40555555f,0.57740587f,0.40555555f,0.57740587f,0.30555555f,0.57182705f,0.14444445f,0.54114366f,0.011111111f,0.44630405f,0.016666668f,0.40446305f,0.15f,0.40446305f,0.30555555f,0.40446305f,0.41111112f,0.41841003f,0.41111112f,0.41841003f,0.4722222f,0.47977686f,0.46666667f,0.47698745f,0.8055556f,0.43654114f,0.85555553f},
				{0.41841003f,1.0f,0.5676429f,0.99444443f,0.54114366f,0.84444445f,0.50488144f,0.8f,0.50488144f,0.47777778f,0.5620642f,0.48333332f,0.56345886f,0.40555555f,0.57740587f,0.40555555f,0.57740587f,0.30555555f,0.57182705f,0.14444445f,0.54114366f,0.011111111f,0.44630405f,0.016666668f,0.40446305f,0.15f,0.40446305f,0.30555555f,0.40446305f,0.41111112f,0.41841003f,0.41111112f,0.41841003f,0.4722222f,0.47977686f,0.46666667f,0.47698745f,0.8055556f,0.43654114f,0.85555553f},
				{0.022624435f,0.0055555557f,0.23076923f,0.2537037f,0.38914028f,0.2537037f,0.39819005f,0.30925927f,0.4524887f,0.31296295f,0.46153846f,0.36296296f,0.49321267f,0.36851853f,0.49321267f,0.9611111f,0.37556562f,0.96666664f,0.36199096f,1.0f,0.64253396f,1.0f,0.6289593f,0.9685185f,0.51583713f,0.9611111f,0.51131225f,0.36851853f,0.5520362f,0.36481482f,0.5520362f,0.31851852f,0.6199095f,0.30925927f,0.6244344f,0.2574074f,0.7873303f,0.2537037f,0.9954751f,0.0f},
				{0.04162331f,1.0f,0.04162331f,0.25f,0.029136317f,0.17777778f,0.036420394f,0.14166667f,0.0020811656f,0.14444445f,0.0020811656f,0.12222222f,0.089490116f,0.119444445f,0.06451613f,0.0f,0.9510926f,0.0f,0.92507803f,0.12222222f,1.0f,0.119444445f,0.9989594f,0.14722222f,0.962539f,0.15f,0.9698231f,0.175f,0.9573361f,0.24722221f,0.9573361f,1.0f},
				{0.004784689f,0.7268519f,0.09090909f,0.7824074f,0.4784689f,0.8101852f,0.36722487f,0.8564815f,0.29186603f,1.0f,0.81220096f,0.7314815f,0.9055024f,0.7824074f,0.99760765f,0.7361111f,0.9868421f,0.5231481f,0.9533493f,0.35185185f,0.90430623f,0.3287037f,0.8672249f,0.16666667f,0.81578946f,0.06944445f,0.76076555f,0.037037037f,0.7248804f,0.060185187f,0.6901914f,0.0046296297f,0.65789473f,0.009259259f,0.611244f,0.12037037f,0.57416266f,0.29166666f,0.54425836f,0.2175926f,0.5f,0.18981482f,0.4389952f,0.24537037f,0.40669855f,0.10185185f,0.35287082f,0.0f,0.32057416f,0.009259259f,0.2930622f,0.06944445f,0.23205742f,0.041666668f,0.16267942f,0.11111111f,0.098086126f,0.31944445f,0.051435407f,0.3564815f,0.043062203f,0.4074074f,0.011961723f,0.5509259f}}};
			
			patchesBounds  = new float[][]{{0.46666667f,0.3453125f,0.4138889f,0.3015625f,0.41944444f,0.425f,0.5861111f,0.4234375f},
										   {0.29444444f,0.35625f,0.28055555f,0.4375f,0.0f,0.434375f,0.0055555557f,0.2984375f,0.23333333f,0.3f},
										   {0.42777777f,0.0984375f,0.375f,0.1578125f,0.0f,0.1578125f,0.0055555557f,0.0421875f,0.38055557f,0.0453125f},
										   {0.46666667f,0.3453125f,0.4138889f,0.3015625f,0.41944444f,0.425f,0.5861111f,0.4234375f},
										   {0.29444444f,0.35625f,0.28055555f,0.4375f,0.0f,0.434375f,0.0055555557f,0.2984375f,0.23333333f,0.3f},
										   {0.42777777f,0.0984375f,0.375f,0.1578125f,0.0f,0.1578125f,0.0055555557f,0.0421875f,0.38055557f,0.0453125f}};
			
			catWidth = catRunFrames[0].getTexture().getWidth()*0.75f*0.5f*Gdx.graphics.getHeight()/1080;
			catHeight = catRunFrames[0].getTexture().getHeight()*0.75f*0.5f*Gdx.graphics.getHeight()/1080;
			dogWidth = dogRunFrames[0].getTexture().getWidth()*0.75f*Gdx.graphics.getHeight()/1080;
			dogHeight = dogRunFrames[0].getTexture().getHeight()*0.75f*Gdx.graphics.getHeight()/1080;
			petsBounds = new float[][]{
				{0.06527778f*catWidth,0.37777779f*catHeight,0.06666667f*catWidth,0.59074074f*catHeight,0.2375f*catWidth,0.6703704f*catHeight,0.35f*catWidth,0.51296294f*catHeight,0.23194444f*catWidth,0.34444445f*catHeight,0.2777778f*catWidth,0.17037037f*catHeight,0.13055556f*catWidth,0.12592593f*catHeight,0.043055557f*catWidth,0.20185184f*catHeight,0.075f*catWidth,0.21481481f*catHeight,0.1736111f*catWidth,0.2685185f*catHeight,0.16666667f*catWidth,0.3314815f*catHeight},
				{0.8138889f*dogWidth,0.66481483f*dogHeight,0.75f*dogWidth,0.68333334f*dogHeight,0.7569444f*dogWidth,0.7462963f*dogHeight,0.67777777f*dogWidth,0.79814816f*dogHeight,0.61527777f*dogWidth,0.77592593f*dogHeight,0.5972222f*dogWidth,0.59814817f*dogHeight,0.7f*dogWidth,0.5388889f*dogHeight,0.7416667f*dogWidth,0.53518516f*dogHeight,0.74444443f*dogWidth,0.5685185f*dogHeight,0.78055555f*dogWidth,0.57222223f*dogHeight}
			};
			
			noodle = new Sprite(assetManager.get("noodle"+String.valueOf(noodleIndex)+".bmp", Texture.class));
			skin = new Sprite(assetManager.get("skin"+String.valueOf(skinIndex)+".bmp", Texture.class));
			needle = new Sprite(assetManager.get("needle"+".png", Texture.class));
			flame = new Sprite(assetManager.get("flame"+".png", Texture.class));
			planeSound = assetManager.get("plane_sound.wav", Sound.class);
			rocketSound = assetManager.get("rocket_sound.wav", Sound.class);
			planeSoundIntense = assetManager.get("plane_sound_intense.wav", Sound.class);
			boost = new Sprite(assetManager.get("boost"+".png", Texture.class));
			meter = new Sprite(assetManager.get("0"+".png", Texture.class));
			
			noodle.setSize(Gdx.graphics.getHeight()/25*noodle.getWidth()/noodle.getHeight(),
				Gdx.graphics.getHeight()/25);
			skin.setSize(noodle.getWidth(), noodle.getHeight());
			needle.setSize(Gdx.graphics.getHeight()/75*needle.getWidth()/needle.getHeight(),
				Gdx.graphics.getHeight()/75);
			flame.setSize(needle.getHeight()*0.9f*flame.getWidth()/flame.getHeight(), needle.getHeight()*0.9f);
			boost.setSize(boost.getWidth()*Gdx.graphics.getHeight()/1080,
				boost.getHeight()*Gdx.graphics.getHeight()/1080);
			meter.setSize(Gdx.graphics.getHeight()/4*1.5f, Gdx.graphics.getHeight()/8*1.5f);
			
			noodle.setOrigin(noodle.getWidth()*7f/12f, noodle.getHeight()/2);
			skin.setOrigin(noodle.getOriginX(), noodle.getOriginY());
			
			noodle.setPosition(0+noodle.getWidth(), Gdx.graphics.getHeight()/2);
			skin.setPosition(noodle.getX(), noodle.getY());
			needle.setPosition(needle.getWidth()/2, Gdx.graphics.getHeight()/4-needle.getHeight()*1.5f);
			flame.setPosition(needle.getX()-flame.getWidth(), needle.getY()+needle.getHeight()*0.05f);
			boost.setPosition(-boost.getWidth(), 0);
			meter.setPosition(Gdx.graphics.getWidth()-meter.getWidth(), 0);
			
			nBounds = new Polygon(new float[]{0.6687631f*noodle.getWidth(),0.9748954f*noodle.getHeight(),0.6750524f*noodle.getWidth(),0.037656903f*noodle.getHeight(),0.01048218f*noodle.getWidth(),0.033472802f*noodle.getHeight(),0.012578616f*noodle.getWidth(),0.16736402f*noodle.getHeight(),0.360587f*noodle.getWidth(),0.18828452f*noodle.getHeight(),0.3668763f*noodle.getWidth(),0.9665272f*noodle.getHeight()});
			nBounds.setOrigin(noodle.getOriginX(), noodle.getOriginY());
			nBounds.setPosition(noodle.getX(), noodle.getY());
			
			neBounds = new Polygon(new float[]{0.15262227f*needle.getWidth(),0.8704454f*needle.getHeight(),0.1543901f*needle.getWidth(),0.15384616f*needle.getHeight(),0.6929876f*needle.getWidth(),0.19838056f*needle.getHeight(),0.689452f*needle.getWidth(),0.8785425f*needle.getHeight()});
			neBounds.setPosition(needle.getX(), needle.getY());
			
			city = new Image(assetManager.get("city"+String.valueOf(MathUtils.random.nextInt(15))+".jpg", Texture.class));
			city.setSize(city.getWidth()*Gdx.graphics.getHeight()/1080f, city.getHeight()*Gdx.graphics.getHeight()/1080f);
			stage.addActor(city);
			
			HILayout = new GlyphLayout();
			HILayout.setText(joystix, "HI");
			
			backgrounds = new Image[4][levelWidth];
			currentBackground = new int[4];
			nextBackground = new int[4];
			obstacles = new Sprite[levelWidth];
			obstacleIndex = new int[levelWidth];
			ceiling = new boolean[levelWidth];
			
			currentBackground[0] = MathUtils.random.nextInt(3);
			currentBackground[1] = MathUtils.random.nextInt(3);
			currentBackground[2] = MathUtils.random.nextInt(2);
			
			iHuman = MathUtils.random.nextInt(levelWidth-1);
			patched = true;
			
			for(int i=0;i<levelWidth;i++)
			{
				if(i!=iHuman)
				{
					ceiling[i] = MathUtils.random.nextBoolean();
					obstacleIndex[i] = MathUtils.random.nextInt(ceiling[i]?5:12);
				}else{
					ceiling[i] = false;
					obstacleIndex[i] = 12+MathUtils.random.nextInt(6);
				}
				Texture wall = assetManager.get("wall"+String.valueOf(currentBackground[0])+".jpg", Texture.class);
				Texture floor = assetManager.get("floor"+String.valueOf(currentBackground[1])+".png", Texture.class);
				isDoor = MathUtils.random.nextBoolean();
				Texture exit = assetManager.get("floor"+String.valueOf(currentBackground[1])
								+(isDoor? "door" : "window")
								+String.valueOf(MathUtils.random.nextInt(2))+".png", Texture.class);
				isNextDoor = MathUtils.random.nextBoolean();		
				TextureRegion entry = new TextureRegion(assetManager.get("floor"+String.valueOf(nextBackground[1])
								+(isNextDoor? "door" : "window")
								+String.valueOf(MathUtils.random.nextInt(2))+".png", Texture.class));
				entry.flip(true, false);
				Texture window = assetManager.get("window"+String.valueOf(currentBackground[2])+String.valueOf(MathUtils.random.nextInt(2))+".png", Texture.class);
				Texture obstacle = assetManager.get("obstacle"+(ceiling[i]?"0":"")+String.valueOf(obstacleIndex[i])+".png", Texture.class);
				
				backgrounds[0][i] = new Image(wall);
				backgrounds[1][i] = new Image(floor);
				if(i==levelWidth-1)
					backgrounds[2][1] = new Image(exit);
				if(i==0)
					backgrounds[2][0] = new Image(entry);
				backgrounds[3][i] = new Image(window);
				if(!(ceiling[i] && obstacleIndex[i]<2))
				{
					if(obstacleIndex[i]==10)
					{
						Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human0Frames);
						humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
						obstacles[i] = new AnimatedSprite(humanAnim);
					}
					else
					if(obstacleIndex[i]==11)
					{
						Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human1Frames);
						humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
						obstacles[i] = new AnimatedSprite(humanAnim);
					}
					else
					if(obstacleIndex[i]==12)
					{
						Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human01Frames);
						humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
						obstacles[i] = new AnimatedSprite(humanAnim);
					}
					else
					if(obstacleIndex[i]==13)
					{
						Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human02Frames);
						humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
						obstacles[i] = new AnimatedSprite(humanAnim);
					}
					else
					if(obstacleIndex[i]==14)
					{
						Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human03Frames);
						humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
						obstacles[i] = new AnimatedSprite(humanAnim);
					}
					else
					if(obstacleIndex[i]==15)
					{
						Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human11Frames);
						humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
						obstacles[i] = new AnimatedSprite(humanAnim);
					}
					else
					if(obstacleIndex[i]==16)
					{
						Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human12Frames);
						humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
						obstacles[i] = new AnimatedSprite(humanAnim);
					}
					else
					if(obstacleIndex[i]==17)
					{
						Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human13Frames);
						humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
						obstacles[i] = new AnimatedSprite(humanAnim);
					}
					else
						obstacles[i] = new Sprite(obstacle);
				}else
				{
					Animation<TextureRegion> fanAnim = new Animation<TextureRegion>(0.017f, obstacleIndex[i]==0?blackFanFrames:whiteFanFrames);
					fanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
					obstacles[i] = new AnimatedSprite(fanAnim);
				}
				backgrounds[0][i].setSize(Gdx.graphics.getHeight()*wall.getWidth()/wall.getHeight(),
							Gdx.graphics.getHeight());
				backgrounds[1][i].setSize(Gdx.graphics.getHeight()*floor.getWidth()/floor.getHeight(),
							Gdx.graphics.getHeight());
				if(i==levelWidth-1)
					backgrounds[2][1].setSize(Gdx.graphics.getHeight()*exit.getWidth()/exit.getHeight(),
							Gdx.graphics.getHeight());
				if(i==0)
					backgrounds[2][0].setSize(Gdx.graphics.getHeight()*entry.getTexture().getWidth()/entry.getTexture().getHeight(),
							Gdx.graphics.getHeight());
				backgrounds[3][i].setSize(Gdx.graphics.getHeight()*0.5f*window.getWidth()/window.getHeight(),
							Gdx.graphics.getHeight()*0.5f);
				obstacles[i].setSize(obstacle.getWidth()*0.75f*Gdx.graphics.getHeight()/1080,
							obstacle.getHeight()*0.75f*Gdx.graphics.getHeight()/1080);
				
				stage.addActor(backgrounds[0][i]);
				stage.addActor(backgrounds[1][i]);
				if(i==levelWidth-1)
					stage.addActor(backgrounds[2][1]);
				if(i==0)
					stage.addActor(backgrounds[2][0]);
				stage.addActor(backgrounds[3][i]);
				
				backgrounds[0][i].setPosition(0+backgrounds[0][0].getWidth()*i, 0);
				backgrounds[1][i].setPosition(0+backgrounds[1][0].getWidth()*i, 0);
				
				if(i==0)
				{
					backgrounds[2][0].setPosition(0-(backgrounds[2][0].getWidth()-backgrounds[1][0].getWidth()), 0);
					obstacles[i].setPosition(backgrounds[0][i].getX()+backgrounds[0][i].getWidth()/2+MathUtils.random((int)(backgrounds[0][i].getWidth()/2-obstacles[i].getWidth())),
								ceiling[i]?Gdx.graphics.getHeight()-obstacles[i].getHeight():Gdx.graphics.getHeight()/4);
				}else
				if(i==levelWidth-1)
				{
					backgrounds[2][1].setPosition(0+backgrounds[1][0].getWidth()*i, 0);
					obstacles[i].setPosition(backgrounds[0][i].getX()+MathUtils.random((int)(backgrounds[0][i].getWidth()/2-obstacles[i].getWidth())),
								ceiling[i]?Gdx.graphics.getHeight()-obstacles[i].getHeight():Gdx.graphics.getHeight()/4);
				}else
					obstacles[i].setPosition(backgrounds[0][i].getX()+MathUtils.random((int)(backgrounds[0][i].getWidth()-obstacles[i].getWidth())),
								ceiling[i]?Gdx.graphics.getHeight()-obstacles[i].getHeight():Gdx.graphics.getHeight()/4);
				backgrounds[3][i].setPosition(0+backgrounds[0][0].getWidth()*i+MathUtils.random.nextInt((int)(backgrounds[0][0].getWidth()-backgrounds[3][i].getWidth())), Gdx.graphics.getHeight()/3);
			}
			city.setPosition(backgrounds[0][levelWidth-1].getX()+backgrounds[0][levelWidth-1].getWidth(), 0);
			
			pauseDown = new ImageButtonDown("button_image.png");
			pause = new Image(new TextureRegionDrawable(assetManager.get("pause.png", Texture.class)));
			pause.setTouchable(Touchable.disabled);restartDown = new ImageButtonDown("button_image.png");
			gameOverWindow = new Image(new TextureRegionDrawable(assetManager.get("window_no_title.png", Texture.class)));
			restartDown = new ImageButtonDown("button_image.png");
			restart = new Image(new TextureRegionDrawable(assetManager.get("restart.png", Texture.class)));
			restart.setTouchable(Touchable.disabled);restartDown = new ImageButtonDown("button_image.png");
			resumeDown = new ImageButtonDown("button_image.png");
			resume = new Image(new TextureRegionDrawable(assetManager.get("play.png", Texture.class)));
			resume.setTouchable(Touchable.disabled);
			homeDown = new ImageButtonDown("button_image.png");
			home = new Image(new TextureRegionDrawable(assetManager.get("home.png", Texture.class)));
			home.setTouchable(Touchable.disabled);
			Label.LabelStyle SKINStyle = new Label.LabelStyle();
					SKINStyle.font = daySansMedium;
					SCORE = new Label("Score : "+String.valueOf(0)+"\n", SKINStyle);
					HSCORE = new Label(""/*"Server Highest : "+String.valueOf(serverHighest)+"\n"*/, SKINStyle);
			Label.LabelStyle labelStyle = new Label.LabelStyle();
					labelStyle.font = daySansLarge;
					title = new Label("GAME OVER!\n", labelStyle);
			
					pauseDown.setSize(buttonImageWidth, buttonImageWidth);
					pause.setSize(buttonImageWidth, buttonImageWidth);
					gameOverWindow.setSize(windowNoTitleSize, windowNoTitleSize);
					restartDown.setSize(buttonImageWidth, buttonImageWidth);
					restart.setSize(buttonImageWidth, buttonImageWidth);
					resumeDown.setSize(buttonImageWidth, buttonImageWidth);
					resume.setSize(buttonImageWidth, buttonImageWidth);
					homeDown.setSize(buttonImageWidth, buttonImageWidth);
					home.setSize(buttonImageWidth, buttonImageWidth);

					pauseDown.setPosition(Gdx.graphics.getWidth()-pauseDown.getWidth(), Gdx.graphics.getHeight()-pauseDown.getHeight());
					pause.setPosition(pauseDown.getX(), pauseDown.getY());

					gameOver.addActor(pauseDown);
					gameOver.addActor(pause);

					pauseDown.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y)
						{
							isGameOver = true;
							paused = true;

							showGameOver();
						}
					});

					restartDown.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y)
						{
							gameScreen = new GameScreen(game);
							setScreen(gameScreen);
						}
					});
					
					resumeDown.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y)
						{
							gameOverWindow.remove();
							title.remove();
							SCORE.remove();
							HSCORE.remove();
							restartDown.remove();
							restart.remove();
							resumeDown.remove();
							resume.remove();
							homeDown.remove();
							home.remove();

							gameOver.addActor(pauseDown);
							gameOver.addActor(pause);

							isGameOver = false;
							paused = false;

							if(SOUND.isChecked())
							{
								planeSoundId = planeSound.loop();
								rocketSoundId = rocketSound.loop();
								planeSoundIntenseId = planeSoundIntense.loop();
							}
						}
					});
					
					homeDown.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y)
						{
							setScreen(mainMenuScreen);
							screenViewport.getCamera().position.x = Gdx.graphics.getWidth()/2;
						}
					});
					
							   /*new Thread() {
									public void run() {
										FTPClient ftpClient = new FTPClient();
										try {
											ftpClient.connect(server, port);
											ftpClient.login(user, pass);
											//ftpClient.enterLocalPassiveMode();
											ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
											
											String firstRemoteFile = "/public_html/"+"highest";
											InputStream inputStream = ftpClient.retrieveFileStream(firstRemoteFile);
											//System.out.println(ftpClient.getReplyCode());
												String userName = readFromStream(inputStream).replace("\n", "");
												inputStream.close();
											ftpClient.completePendingCommand();
											String secondRemoteFile = "/public_html/"+userName;
											InputStream inputStream1 = ftpClient.retrieveFileStream(secondRemoteFile);
											//System.out.println(ftpClient.getReplyCode());
											if(!(inputStream1==null))
											{
												serverHighest = Integer.parseInt(readFromStream(inputStream1).replace("\n", ""));
												inputStream1.close();
												HSCORE.setText("Server Highest : "+String.valueOf(serverHighest)+"\n");
												HSCORE.setPosition(gameOverWindow.getX()+windowNoTitleSize/2-HSCORE.getWidth()/2, gameOverWindow.getY()+windowNoTitlePadding+restartDown.getHeight());
											}
										} catch (IOException ex) {
										} finally {
											try {
												if (ftpClient.isConnected()) {
													ftpClient.logout();
													ftpClient.disconnect();
												}
											} catch (IOException ex){}
										}
									}
								}.start();*/
		}
		
		public void render(final float delta)
		{
			if(!isGameOver)
			{
				if(screenViewport.getCamera().position.x-Gdx.graphics.getWidth()/2>=backgrounds[2][1].getX()
					&& backgrounds[0][0].getX()<backgrounds[2][1].getX())
				{
					hadPet = petIndex>0;
					if(!hadPet)
						petIndex = MathUtils.random.nextInt(3);
					
					ceiling[0] = MathUtils.random.nextBoolean();
					nextBackground[0] = MathUtils.random.nextInt(3);
					nextBackground[1] = MathUtils.random.nextInt(3);
					nextBackground[2] = MathUtils.random.nextInt(2);
					obstacleIndex[0] = MathUtils.random.nextInt(ceiling[0]?5:11);
				
					Texture wall = assetManager.get("wall"+String.valueOf(nextBackground[0])+".jpg", Texture.class);
					Texture floor = assetManager.get("floor"+String.valueOf(nextBackground[1])+".png", Texture.class);		
					isNextDoor = MathUtils.random.nextBoolean();		
					TextureRegion entry = new TextureRegion(assetManager.get("floor"+String.valueOf(nextBackground[1])
									+(isNextDoor? "door" : "window")
									+String.valueOf(MathUtils.random.nextInt(2))+".png", Texture.class));
					entry.flip(true, false);
					Texture window = assetManager.get("window"+String.valueOf(nextBackground[2])+String.valueOf(MathUtils.random.nextInt(2))+".png", Texture.class);
					Texture obstacle = assetManager.get("obstacle"+(ceiling[0]?"0":"")+String.valueOf(obstacleIndex[0])+".png", Texture.class);
					
					backgrounds[0][0].setDrawable(new TextureRegionDrawable(wall));
					backgrounds[1][0].setDrawable(new TextureRegionDrawable(floor));
					backgrounds[2][0].setDrawable(new TextureRegionDrawable(entry));
					backgrounds[3][0].setDrawable(new TextureRegionDrawable(window));
					if(!(ceiling[0] && obstacleIndex[0]<2))
					{
						if(obstacleIndex[0]==10)
						{
							Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human0Frames);
							humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
							obstacles[0] = new AnimatedSprite(humanAnim);
						}
						else
						if(obstacleIndex[0]==11)
						{
							Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human1Frames);
							humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
							obstacles[0] = new AnimatedSprite(humanAnim);
						}
						else
						if(obstacleIndex[0]==12)
						{
							Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human01Frames);
							humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
							obstacles[0] = new AnimatedSprite(humanAnim);
						}
						else
						if(obstacleIndex[0]==13)
						{
							Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human02Frames);
							humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
							obstacles[0] = new AnimatedSprite(humanAnim);
						}
						else
						if(obstacleIndex[0]==14)
						{
							Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human03Frames);
							humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
							obstacles[0] = new AnimatedSprite(humanAnim);
						}
						else
						if(obstacleIndex[0]==15)
						{
							Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human11Frames);
							humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
							obstacles[0] = new AnimatedSprite(humanAnim);
						}
						else
						if(obstacleIndex[0]==16)
						{
							Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human12Frames);
							humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
							obstacles[0] = new AnimatedSprite(humanAnim);
						}
						else
						if(obstacleIndex[0]==17)
						{
							Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human13Frames);
							humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
							obstacles[0] = new AnimatedSprite(humanAnim);
						}
						else
							obstacles[0] = new Sprite(obstacle);
					}else
					{
						Animation<TextureRegion> fanAnim = new Animation<TextureRegion>(0.017f, obstacleIndex[0]==0?blackFanFrames:whiteFanFrames);
						fanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
						obstacles[0] = new AnimatedSprite(fanAnim);
					}
					if(petIndex>0 && !hadPet)
					{
						petRunAnim = null;
						petIdleAnim = new Animation<TextureRegion>(0.017f, petIndex==1?catIdleFrames:dogIdleFrames);
						petIdleAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
						pet = new AnimatedSprite(petIdleAnim);
					}
					
					backgrounds[0][0].setSize(Gdx.graphics.getHeight()*wall.getWidth()/wall.getHeight(),
								Gdx.graphics.getHeight());
					backgrounds[1][0].setSize(Gdx.graphics.getHeight()*floor.getWidth()/floor.getHeight(),
								Gdx.graphics.getHeight());
					backgrounds[2][0].setSize(Gdx.graphics.getHeight()*entry.getTexture().getWidth()/entry.getTexture().getHeight(),
								Gdx.graphics.getHeight());
					backgrounds[3][0].setSize(Gdx.graphics.getHeight()*0.5f*window.getWidth()/window.getHeight(),
								Gdx.graphics.getHeight()*0.5f);
					obstacles[0].setSize(obstacle.getWidth()*0.75f*Gdx.graphics.getHeight()/1080,
								obstacle.getHeight()*0.75f*Gdx.graphics.getHeight()/1080);
					if(!hadPet)
					{
						if(petIndex>0)
						{
							pet.setSize(pet.getWidth()*0.75f*Gdx.graphics.getHeight()/1080,
								pet.getHeight()*0.75f*Gdx.graphics.getHeight()/1080);
							pet.setOrigin(pet.getWidth()/2, pet.getHeight()/2);
						}
						if(petIndex==1)
							pet.setSize(pet.getWidth()*0.5f,
									pet.getHeight()*0.5f);
					}
					backgrounds[0][0].setPosition(backgrounds[2][1].getX()+backgrounds[2][1].getWidth()+MathUtils.random(backgrounds[2][0].getWidth()-backgrounds[1][0].getWidth(),Gdx.graphics.getWidth()), 0);
					backgrounds[1][0].setPosition(backgrounds[0][0].getX(), 0);
					backgrounds[2][0].setPosition(backgrounds[1][0].getX()-(backgrounds[2][0].getWidth()-backgrounds[1][0].getWidth()), 0);
					backgrounds[3][0].setPosition(backgrounds[0][0].getX()+MathUtils.random.nextInt((int)(backgrounds[0][0].getWidth()-backgrounds[3][0].getWidth())), Gdx.graphics.getHeight()/3);
					obstacles[0].setPosition(backgrounds[0][0].getX()+backgrounds[0][0].getWidth()/2+MathUtils.random((int)(backgrounds[0][0].getWidth()/2-obstacles[0].getWidth())),
									ceiling[0]?Gdx.graphics.getHeight()-obstacles[0].getHeight():Gdx.graphics.getHeight()/4);
					if(petIndex>0 && !hadPet)
						pet.setPosition(backgrounds[0][0].getX()+MathUtils.random((int)(backgrounds[0][0].getWidth()/2-pet.getWidth())),
							pet.getHeight()/2);
				}
				
				if(screenViewport.getCamera().position.x-Gdx.graphics.getWidth()/2>=backgrounds[0][0].getX()
									&& backgrounds[0][1].getX()<backgrounds[0][0].getX())
					{
									levelWidth = MathUtils.random(4, backgrounds[0].length);
									currentBackground = nextBackground;
									
								iHuman = 1+MathUtils.random.nextInt(levelWidth-2);
								patched = true;
								if(hadPet)petIndex = 0;
								
								for(int i=1;i<levelWidth;i++)
								{
									if(i!=iHuman)
									{
										ceiling[i] = MathUtils.random.nextBoolean();
										obstacleIndex[i] = MathUtils.random.nextInt(ceiling[i]?5:12);
									}else{
										ceiling[i] = false;
										obstacleIndex[i] = 12+MathUtils.random.nextInt(6);
									}
									Texture wall = assetManager.get("wall"+String.valueOf(currentBackground[0])+".jpg", Texture.class);
									Texture floor = assetManager.get("floor"+String.valueOf(currentBackground[1])+".png", Texture.class);
									isDoor = MathUtils.random.nextBoolean();
									Texture exit = assetManager.get("floor"+String.valueOf(currentBackground[1])
													+(isDoor? "door" : "window")
													+String.valueOf(MathUtils.random.nextInt(2))+".png", Texture.class);
									Texture window = assetManager.get("window"+String.valueOf(nextBackground[2])+String.valueOf(MathUtils.random.nextInt(2))+".png", Texture.class);
									Texture obstacle = assetManager.get("obstacle"+(ceiling[i]?"0":"")+String.valueOf(obstacleIndex[i])+".png", Texture.class);
									
									backgrounds[0][i].setDrawable(new TextureRegionDrawable(wall));
									backgrounds[1][i].setDrawable(new TextureRegionDrawable(floor));
									if(i==levelWidth-1)
									{
										backgrounds[2][1].setDrawable(new TextureRegionDrawable(exit));
										city.setDrawable(new TextureRegionDrawable(assetManager.get("city"+String.valueOf(MathUtils.random.nextInt(15))+".jpg", Texture.class)));
										city.setSize(assetManager.get("city"+String.valueOf(MathUtils.random.nextInt(15))+".jpg", Texture.class).getWidth(), assetManager.get("city"+String.valueOf(MathUtils.random.nextInt(15))+".jpg", Texture.class).getHeight());
									}
									backgrounds[3][i].setDrawable(new TextureRegionDrawable(window));
									if(!(ceiling[i] && obstacleIndex[i]<2))
									{
										if(obstacleIndex[i]==10)
										{
											Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human0Frames);
											humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
											obstacles[i] = new AnimatedSprite(humanAnim);
										}
										else
										if(obstacleIndex[i]==11)
										{
											Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human1Frames);
											humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
											obstacles[i] = new AnimatedSprite(humanAnim);
										}
										else
										if(obstacleIndex[i]==12)
										{
											Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human01Frames);
											humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
											obstacles[i] = new AnimatedSprite(humanAnim);
										}
										else
										if(obstacleIndex[i]==13)
										{
											Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human02Frames);
											humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
											obstacles[i] = new AnimatedSprite(humanAnim);
										}
										else
										if(obstacleIndex[i]==14)
										{
											Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human03Frames);
											humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
											obstacles[i] = new AnimatedSprite(humanAnim);
										}
										else
										if(obstacleIndex[i]==15)
										{
											Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human11Frames);
											humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
											obstacles[i] = new AnimatedSprite(humanAnim);
										}
										else
										if(obstacleIndex[i]==16)
										{
											Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human12Frames);
											humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
											obstacles[i] = new AnimatedSprite(humanAnim);
										}
										else
										if(obstacleIndex[i]==17)
										{
											Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, human13Frames);
											humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
											obstacles[i] = new AnimatedSprite(humanAnim);
										}
										else
											obstacles[i] = new Sprite(obstacle);
									}else
									{
										Animation<TextureRegion> fanAnim = new Animation<TextureRegion>(0.017f, obstacleIndex[i]==0?blackFanFrames:whiteFanFrames);
										fanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
										obstacles[i] = new AnimatedSprite(fanAnim);
									}
									
									backgrounds[0][i].setSize(Gdx.graphics.getHeight()*wall.getWidth()/wall.getHeight(),
												Gdx.graphics.getHeight());
									backgrounds[1][i].setSize(Gdx.graphics.getHeight()*floor.getWidth()/floor.getHeight(),
												Gdx.graphics.getHeight());
									if(i==levelWidth-1)
									{
										backgrounds[2][1].setSize(Gdx.graphics.getHeight()*exit.getWidth()/exit.getHeight(),
												Gdx.graphics.getHeight());
										city.setSize(city.getWidth()*Gdx.graphics.getHeight()/1080f, city.getHeight()*Gdx.graphics.getHeight()/1080f);
									}
									backgrounds[3][i].setSize(Gdx.graphics.getHeight()*0.5f*window.getWidth()/window.getHeight(),
												Gdx.graphics.getHeight()*0.5f);
									obstacles[i].setSize(obstacle.getWidth()*0.75f*Gdx.graphics.getHeight()/1080,
										obstacle.getHeight()*0.75f*Gdx.graphics.getHeight()/1080);
									
									backgrounds[0][i].setPosition(backgrounds[0][0].getX()+backgrounds[0][0].getWidth()*i, 0);
									backgrounds[1][i].setPosition(backgrounds[1][0].getX()+backgrounds[1][0].getWidth()*i, 0);
									if(i==levelWidth-1)
									{
										backgrounds[2][1].setPosition(backgrounds[1][0].getX()+backgrounds[1][0].getWidth()*i, 0);
										city.setPosition(backgrounds[0][i].getX()+backgrounds[0][i].getWidth(), 0);
										obstacles[i].setPosition(backgrounds[0][i].getX()+MathUtils.random((int)(backgrounds[0][i].getWidth()/2-obstacles[i].getWidth())),
											ceiling[i]?Gdx.graphics.getHeight()-obstacles[i].getHeight():Gdx.graphics.getHeight()/4);
									}
									else
										obstacles[i].setPosition(backgrounds[0][i].getX()+MathUtils.random((int)(backgrounds[0][i].getWidth()-obstacles[i].getWidth())),
											ceiling[i]?Gdx.graphics.getHeight()-obstacles[i].getHeight():Gdx.graphics.getHeight()/4);
									backgrounds[3][i].setPosition(backgrounds[0][0].getX()+(backgrounds[0][0].getWidth()*i)+MathUtils.random.nextInt((int)(backgrounds[0][i].getWidth()-backgrounds[3][i].getWidth())), Gdx.graphics.getHeight()/3);
								}
					}
			
				for(int i=0;i<levelWidth;i++)
				{
					final int iFinal = i;
					float[] obstacleBoundsTmp = obstaclesBounds[ceiling[i]?1:0][obstacleIndex[i]];
					float[] obstacleBounds = new float[obstacleBoundsTmp.length];
					for(int p = 0;p<obstacleBoundsTmp.length;p++)
						if(p%2==0)
							obstacleBounds[p] = obstacleBoundsTmp[p]*obstacles[i].getWidth();
						else
							obstacleBounds[p] = obstacleBoundsTmp[p]*obstacles[i].getHeight();
					Polygon oBounds = new Polygon(obstacleBounds);
					oBounds.setPosition(obstacles[i].getX(), obstacles[i].getY());
					
					Polygon pBounds = new Polygon();
					
					if(Intersector.overlapConvexPolygons(oBounds, neBounds))
					{
						dScore2 = dScore;
						if(obstacleIndex[i]>11)
							{
								float[] patchBoundsTmp = patchesBounds[obstacleIndex[i]-12];
								float[] patchBounds = new float[patchBoundsTmp.length];
								for(int p = 0;p<patchBoundsTmp.length;p++)
									if(p%2==0)
										patchBounds[p] = patchBoundsTmp[p]*obstacles[i].getWidth();
									else
										patchBounds[p] = patchBoundsTmp[p]*obstacles[i].getHeight();
								pBounds = new Polygon(patchBounds);
								pBounds.setPosition(obstacles[i].getX(), obstacles[i].getY());
								
								if(Intersector.overlapConvexPolygons(pBounds, neBounds))
								{
									score -= 1*25f/60f;
									if(patched)
									{
										if(neSpeed>0)
										{
											if(pScore>-5)
												pScore -= 1;
											meter.setTexture(assetManager.get(String.valueOf(pScore)+".png", Texture.class));
											meter.setSize(Gdx.graphics.getHeight()/4*1.5f, Gdx.graphics.getHeight()/8*1.5f);
										
											if(SOUND.isChecked())
											{
												if(needle.getX()+needle.getWidth()>screenViewport.getCamera().position.x-Gdx.graphics.getWidth()/2
													&& needle.getX()<screenViewport.getCamera().position.x+Gdx.graphics.getWidth()/2)
													assetManager.get("blood.wav", Sound.class).play();
											}
											patched = false;
											
											float oWidth = obstacles[i].getWidth();
											float oHeight = obstacles[i].getHeight();
											
											float oX = obstacles[i].getX();
											float oY = obstacles[i].getY();
											
													Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, obstacleIndex[i]<15?human00Frames:human10Frames);
													humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
													obstacles[i] = new AnimatedSprite(humanAnim);
											
											obstacles[i].setSize(oWidth, oHeight);
											obstacles[i].setPosition(oX, oY);
											
											Timer.schedule(new Timer.Task(){
												
												int deltaDegree = 0;
												@Override
												public void run()
												{
													dScore2 = dScore;
													neSpeed = (10*Gdx.graphics.getHeight())/1080;
													
													float oWidth = obstacles[iFinal].getWidth();
													float oHeight = obstacles[iFinal].getHeight();
													
													float oX = obstacles[iFinal].getX();
													float oY = obstacles[iFinal].getY();
													
															Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, obstacleIndex[iFinal]<15?human0Frames:human1Frames);
															humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
															obstacles[iFinal] = new AnimatedSprite(humanAnim);
													
													obstacles[iFinal].setSize(oWidth, oHeight);
													obstacles[iFinal].setPosition(oX, oY);										
												}
											}, 1f);
										}
										neSpeed = 0;
									}
								}
							}
					}
					if(Intersector.overlapConvexPolygons(oBounds, nBounds))
					{
						if(obstacleIndex[i]>11)
						{
								float[] patchBoundsTmp = patchesBounds[obstacleIndex[i]-12];
								float[] patchBounds = new float[patchBoundsTmp.length];
								for(int p = 0;p<patchBoundsTmp.length;p++)
									if(p%2==0)
										patchBounds[p] = patchBoundsTmp[p]*obstacles[i].getWidth();
									else
										patchBounds[p] = patchBoundsTmp[p]*obstacles[i].getHeight();
								pBounds = new Polygon(patchBounds);
								pBounds.setPosition(obstacles[i].getX(), obstacles[i].getY());
					
								final int patchX = (int)obstacles[i].getX()+(int)(patchesBounds[obstacleIndex[i]-12][0]*(float)obstacles[i].getWidth());			
								final int patchY = (int)obstacles[i].getY()+(int)(patchesBounds[obstacleIndex[i]-12][1]*(float)obstacles[i].getHeight());
								
							if(Intersector.overlapConvexPolygons(pBounds, nBounds))
							{
								score += 1*100f/60f;
								if(patched)
								{
									if(nSpeed>0)
									{
										if(pScore<5)
											pScore += 1;
										meter.setTexture(assetManager.get(String.valueOf(pScore)+".png", Texture.class));
										meter.setSize(Gdx.graphics.getHeight()/4*1.5f, Gdx.graphics.getHeight()/8*1.5f);
										noodle.setPosition(patchX-noodle.getHeight()*1.75f, patchY);
										if(SOUND.isChecked())
										{
											assetManager.get("blood.wav", Sound.class).play();
										}
										patched = false;
										
										noodle.setRotation(75);
										skin.setRotation(noodle.getRotation());
										nBounds.setRotation(noodle.getRotation());
										if(obstacleIndex[i]==13||obstacleIndex[i]==16)
											noodle.setPosition(noodle.getX()+obstacles[i].getWidth()/20, noodle.getY());
										
										float oWidth = obstacles[i].getWidth();
										float oHeight = obstacles[i].getHeight();
										
										float oX = obstacles[i].getX();
										float oY = obstacles[i].getY();
										
												Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, obstacleIndex[i]<15?human000Frames:human100Frames);
												humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
												obstacles[i] = new AnimatedSprite(humanAnim);
										
										obstacles[i].setSize(oWidth, oHeight);
										obstacles[i].setPosition(oX, oY);
										
										
										Timer.schedule(new Timer.Task(){
											
											int deltaDegree = 0;
											@Override
											public void run()
											{
												/*noodle.setRotation(0f);
												skin.setRotation(noodle.getRotation());
												nBounds.setRotation(noodle.getRotation());*/
												nSpeed = (10*Gdx.graphics.getHeight())/1080*(boosted?1.5f : 1f);
												omegaMax = 2;
												if(petIndex>0)
													((AnimatedSprite)pet).animation = petRunAnim;
												noodle.setPosition(noodle.getX()-noodle.getWidth()/2-nSpeed, noodle.getY());
												if(obstacleIndex[iFinal]==13||obstacleIndex[iFinal]==16)
													noodle.setPosition(noodle.getX()-obstacles[iFinal].getWidth()/3, noodle.getY());
												skin.setPosition(noodle.getX(), noodle.getY());
												nBounds.setPosition(noodle.getX(), noodle.getY());
												screenViewport.getCamera().position.x = noodle.getX()-noodle.getWidth()+Gdx.graphics.getWidth()/2;
												
												float oWidth = obstacles[iFinal].getWidth();
												float oHeight = obstacles[iFinal].getHeight();
												
												float oX = obstacles[iFinal].getX();
												float oY = obstacles[iFinal].getY();
												
														Animation<TextureRegion> humanAnim = new Animation<TextureRegion>(0.017f, obstacleIndex[iFinal]<15?human0Frames:human1Frames);
														humanAnim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
														obstacles[iFinal] = new AnimatedSprite(humanAnim);
												
												obstacles[iFinal].setSize(oWidth, oHeight);
												obstacles[iFinal].setPosition(oX, oY);
											}
										}, 1f);
									}
									nSpeed = 0;
									if(petIndex>0)
										((AnimatedSprite)pet).animation = petIdleAnim;
								}
							}
							else{
									if(SOUND.isChecked() && dScore>dScore1+3)assetManager.get("hit.wav", Sound.class).play();dScore1 = dScore;
								}
						}
						else{
									if(SOUND.isChecked() && dScore>dScore1+3)assetManager.get("hit.wav", Sound.class).play();dScore1 = dScore;
								}
					}
				}
				
				if((noodle.getX()+noodle.getWidth()>backgrounds[0][levelWidth-1].getX()+backgrounds[0][levelWidth-1].getWidth()
					&& noodle.getX()+noodle.getWidth()<backgrounds[0][levelWidth-1].getX()+backgrounds[0][levelWidth-1].getWidth()+nSpeed*2f))
				{
					if(noodle.getY()>(1080-225)*Gdx.graphics.getHeight()/1080f
						|| (!isDoor && noodle.getY()<(1080-815)*Gdx.graphics.getHeight()/1080f))
					{
						if(SOUND.isChecked() && dScore>dScore1+3)assetManager.get("hit.wav", Sound.class).play();dScore1 = dScore;
					}
				}
				else
				if(noodle.getX()+noodle.getWidth()<backgrounds[0][0].getX()
						&& noodle.getX()+noodle.getWidth()>backgrounds[0][0].getX()-nSpeed*2f)
				{
					if(noodle.getY()>(1080-225)*Gdx.graphics.getHeight()/1080f
						|| (!isNextDoor && noodle.getY()<(1080-815)*Gdx.graphics.getHeight()/1080f))
					{
						if(SOUND.isChecked() && dScore>dScore1+3)assetManager.get("hit.wav", Sound.class).play();dScore1 = dScore;
					}
				}
				
				if(noodle.getBoundingRectangle().overlaps(boost.getBoundingRectangle()) && !boosted)
				{
					boosted = true;
					final float nSp = nSpeed;
					Timer.schedule(new Timer.Task(){
						float time = 0f;
							@Override
							public void run()
							{
								nSpeed = nSp+nSp*1f*(1f-(float)MathUtils.cos(time/0.5f*MathUtils.PI/2));
								time += delta;
							}
					}, 0f, delta, 30);
					if(SOUND.isChecked())
						Timer.schedule(new Timer.Task(){
							@Override
							public void run()
							{
								assetManager.get("powerup.wav", Sound.class).play();
							}
						}, delta*17);
					Timer.schedule(new Timer.Task(){
							@Override
							public void run()
							{
								boosted = false;
								nSpeed = nSp;
							}
					}, Math.max(((Gdx.graphics.getWidth()-noodle.getWidth())*boostFactor)/(float)(nSpeed*2f-neSpeed)*delta, 15*delta));
				}
				
				if(SOUND.isChecked())
				{
					planeSound.setVolume(planeSoundId, nSpeed==0?0f:1f);
					rocketSound.setVolume(rocketSoundId, neSpeed==0?0f : 1f-(noodle.getX()<needle.getX()?(float)Math.sqrt((needle.getX()-noodle.getX())*(needle.getX()-noodle.getX())+(needle.getY()-noodle.getY())*(needle.getY()-noodle.getY()))/(float)Math.sqrt((Gdx.graphics.getWidth()-noodle.getWidth())*(Gdx.graphics.getWidth()-noodle.getWidth())+(Gdx.graphics.getHeight()*3f/4f)*(Gdx.graphics.getHeight()*3f/4f))
																					: (float)(noodle.getX()-needle.getX())/(float)(noodle.getWidth()+needle.getWidth())));
				}
				
				if(TILT.isChecked())
				{
					final int threshold = 4;
					if((Gdx.input.getAccelerometerY()-accPrev)*-9>threshold)
					{
						Timer.schedule(new Timer.Task(){
							int deltaDegree = 0;
							@Override
							public void run()
							{
								noodle.setRotation(noodle.getRotation()+0.1f);
								deltaDegree++;
								if(deltaDegree==threshold*10)
									Timer.schedule(new Timer.Task(){
										int frame = 0;
										@Override
										public void run()
										{
											noodle.setRotation(noodle.getRotation()+MathUtils.cos((float)frame/(float)threshold*10*MathUtils.PI/2f));
											frame++;
										}
									}, delta, delta, threshold*10-1);
							}
						}, delta, delta, threshold*10-1);
						accPrev = Gdx.input.getAccelerometerY();
					}else
						if((Gdx.input.getAccelerometerY()-accPrev)*-9<-threshold)
						{
							Timer.schedule(new Timer.Task(){
								int deltaDegree = 0;
								@Override
								public void run()
								{
									noodle.setRotation(noodle.getRotation()-0.1f);
									deltaDegree++;
									if(deltaDegree==threshold*10)
										Timer.schedule(new Timer.Task(){
											int frame = 0;
											@Override
											public void run()
											{
												noodle.setRotation(noodle.getRotation()-MathUtils.cos((float)frame/(float)threshold*10*MathUtils.PI/2f));
												frame++;
											}
										}, delta, delta, threshold*10-1);
								}
							}, delta, delta, threshold*10-1);
							accPrev = Gdx.input.getAccelerometerY();
						}
				}
					
				if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.UP)
					|| (Gdx.input.isTouched() && Gdx.input.getX()<Gdx.graphics.getWidth()/2))
					alpha = 0.1f;
				else
					if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.DOWN)
						|| (Gdx.input.isTouched() && Gdx.input.getX()>Gdx.graphics.getWidth()/2))
						alpha = -0.1f;
					else
					{
						alpha = 0;
						if(omega==omegaMax)
						{
							Timer.schedule(new Timer.Task(){
										int frame = 0;
										@Override
										public void run()
										{
											omega = omegaMax*MathUtils.cos(frame/omegaMax*MathUtils.PI/2);
											frame++;
										}
									}, delta, delta, (int)omegaMax);
						}else
						if(omega==-omegaMax)
						{
							Timer.schedule(new Timer.Task(){
										int frame = 0;
										@Override
										public void run()
										{
											omega = -omegaMax*MathUtils.cos(frame/omegaMax*MathUtils.PI/2);
											frame++;
										}
									}, delta, delta, (int)omegaMax);
						}else
						{
							if(omega!=0)
							{
								Timer.schedule(new Timer.Task(){
										int frame = 0;
										float omegaTop = omega;
										@Override
										public void run()
										{
											omega = omegaTop*MathUtils.cos(frame/omegaMax*MathUtils.PI/2);
											frame++;
										}
									}, delta, delta, (int)(omega>0?omega : -omega)+1);
							}
						}
					}
				
				omega = MathUtils.clamp(omega+alpha, -omegaMax, omegaMax);
				if(SOUND.isChecked())
				{
					planeSound.setVolume(planeSoundId, 1f-(float)(omega<0?-omega:omega)/(float)omegaMax);
					planeSoundIntense.setVolume(planeSoundIntenseId, 0.2f+0.8f*(float)(omega<0?-omega:omega)/(float)omegaMax);
				}
				noodle.setRotation(MathUtils.clamp(noodle.getRotation()+omega, -75, 75));
				skin.setRotation(noodle.getRotation());
				nBounds.setRotation(noodle.getRotation());
			
				if(needle.getX()-noodle.getX()>backgrounds[0][0].getWidth()*1.5f)
				{
					nSpeed = 0;
					isGameOver = true;
					showGameOver();
				}
				
				if(nSpeed*neSpeed > 0 && !boosted)
				{
					nSpeed = ((10f+(dScore-dScore1)/20f)*Gdx.graphics.getHeight())/1080f;
					neSpeed = (int)(((10f+(dScore-dScore2)/20f)*Gdx.graphics.getHeight())/1080f);
				}
				if(petIndex>0 && ((AnimatedSprite)pet).animation==petRunAnim && pet.getY()==pet.getHeight()/2)
					((AnimatedSprite)pet).animation.setFrameDuration(0.017f/(nSpeed/((10f*Gdx.graphics.getHeight())/1080f)));
			}

			
			pauseDown.setPosition(screenViewport.getCamera().position.x+Gdx.graphics.getWidth()/2-pauseDown.getWidth(), Gdx.graphics.getHeight()-pauseDown.getHeight());
			pause.setPosition(pauseDown.getX(), pauseDown.getY());
			meter.setPosition(screenViewport.getCamera().position.x+Gdx.graphics.getWidth()/2-meter.getWidth(), 0);
			
			stage.act(Gdx.graphics.getDeltaTime());
			gameOver.act(Gdx.graphics.getDeltaTime());
			
			stage.draw();
			spriteBatch.begin();
			for(int i=0;i<obstacles.length;i++)
				if(obstacles[i].getX()+obstacles[i].getWidth()>screenViewport.getCamera().position.x-Gdx.graphics.getWidth()/2
					&& obstacles[i].getX()<screenViewport.getCamera().position.x+Gdx.graphics.getWidth()/2)
				obstacles[i].draw(spriteBatch);
			if(!boosted)
				if(boost.getX()+boost.getWidth()>screenViewport.getCamera().position.x-Gdx.graphics.getWidth()/2
					&& boost.getX()<screenViewport.getCamera().position.x+Gdx.graphics.getWidth()/2)
					boost.draw(spriteBatch);
			noodle.draw(spriteBatch);
			if(nSpeed>0)
				skin.draw(spriteBatch);
			if(neSpeed>0)
				if(flame.getX()+flame.getWidth()>screenViewport.getCamera().position.x-Gdx.graphics.getWidth()/2
					&& flame.getX()<screenViewport.getCamera().position.x+Gdx.graphics.getWidth()/2)
					flame.draw(spriteBatch);
			if(needle.getX()+needle.getWidth()>screenViewport.getCamera().position.x-Gdx.graphics.getWidth()/2
				&& needle.getX()<screenViewport.getCamera().position.x+Gdx.graphics.getWidth()/2)
				needle.draw(spriteBatch);
			if(petIndex>0)
				if(pet.getX()+pet.getWidth()>screenViewport.getCamera().position.x-Gdx.graphics.getWidth()/2
					&& pet.getX()<screenViewport.getCamera().position.x+Gdx.graphics.getWidth()/2)
					pet.draw(spriteBatch);
			joystix.draw(spriteBatch, " HI "+String.valueOf(HScore)+" "+String.valueOf((int)score+dScore), screenViewport.getCamera().position.x-Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-HILayout.height);
			meter.draw(spriteBatch);
			spriteBatch.end();
			gameOver.draw();
				
			/*shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			shapeRenderer.setColor(Color.RED);
				shapeRenderer.polygon(nBounds.getTransformedVertices());
			shapeRenderer.end();*/
			
			if(!isGameOver)
			{
				if(needle.getX()-noodle.getX()>(Gdx.graphics.getWidth()-noodle.getWidth()-needle.getWidth()) && boost.getX()<noodle.getX() && !boosted)
					for(int i=0;i<levelWidth;i++)
					{
						if(obstacles[i].getX()-noodle.getX()>Gdx.graphics.getWidth()-noodle.getWidth())
						{
							boostFactor = 0.5f+MathUtils.random.nextFloat()*0.4f;
							boost.setSize(66*Gdx.graphics.getHeight()/1080f*boostFactor, 256*Gdx.graphics.getHeight()/1080f*boostFactor);
							boost.setPosition(obstacles[i].getX()+MathUtils.random.nextInt((int)(obstacles[i].getWidth()-boost.getWidth())), ceiling[i]?Gdx.graphics.getHeight()/4+boost.getHeight()/20f+MathUtils.random.nextInt((int)(256*Gdx.graphics.getHeight()/1080f-boost.getHeight())) : Gdx.graphics.getHeight()*3/4+MathUtils.random.nextInt((int)(256*Gdx.graphics.getHeight()/1080f-boost.getHeight())));
								Timer.schedule(new Timer.Task(){
										float angle = 0f;
										float Y = 0f;
										@Override
										public void run()
										{
											if(Y==0f)
												Y=boost.getY();
											if(boost.getX()+boost.getWidth()<screenViewport.getCamera().position.x-Gdx.graphics.getWidth()/2)
												cancel();
											boost.setPosition(boost.getX(), Y+boost.getHeight()*5f/100f*(float)MathUtils.sin(angle));
											angle+=3f/180f*(float)MathUtils.PI;
										}
									}, delta, delta);
							break;
						}
					}
				
				final int nSpeedX = (int)(nSpeed*MathUtils.cos(noodle.getRotation()/180f*MathUtils.PI));
				int nSpeedY = (int)(nSpeed*MathUtils.sin(noodle.getRotation()/180f*MathUtils.PI));
				
				noodle.setPosition(noodle.getX()+nSpeedX,
					MathUtils.clamp(noodle.getY()+nSpeedY, Gdx.graphics.getHeight()/4+(noodle.getWidth()-noodle.getOriginX())/2*MathUtils.sin((noodle.getRotation()<0?-noodle.getRotation():noodle.getRotation())/180f*MathUtils.PI), Gdx.graphics.getHeight()-noodle.getHeight()));
				skin.setPosition(noodle.getX(), noodle.getY());
				nBounds.setPosition(noodle.getX(), noodle.getY());
				needle.setPosition(needle.getX()+neSpeed*(needle.getX()+needle.getWidth()>screenViewport.getCamera().position.x-Gdx.graphics.getWidth()/2?0.9f:1.1f),
					Gdx.graphics.getHeight()/4-needle.getHeight()*1.5f);
				screenViewport.getCamera().translate(nSpeedX, 0, 0);
				
				if(backgrounds[0][levelWidth-1].getX()+backgrounds[0][levelWidth-1].getWidth()-needle.getX()<backgrounds[0][0].getWidth()/2
					&& backgrounds[0][levelWidth-1].getX()+backgrounds[0][levelWidth-1].getWidth()-needle.getX()>0)
					needle.setPosition(needle.getX(), Gdx.graphics.getHeight()/4+noodle.getHeight()-((Gdx.graphics.getHeight()/4+noodle.getHeight())-(Gdx.graphics.getHeight()/4-needle.getHeight()*1.5f))*(backgrounds[0][levelWidth-1].getX()+backgrounds[0][levelWidth-1].getWidth()-needle.getX())/(backgrounds[0][0].getWidth()/2));
				else
					if(backgrounds[0][levelWidth-1].getX()+backgrounds[0][levelWidth-1].getWidth()<needle.getX()
						&& backgrounds[0][0].getX()>needle.getX())
						needle.setPosition(needle.getX(), Gdx.graphics.getHeight()/4+noodle.getHeight());
					else
						if(backgrounds[0][0].getX()<needle.getX()
							&& needle.getX()-backgrounds[0][0].getX()<backgrounds[0][0].getWidth()/2)
							needle.setPosition(needle.getX(), Gdx.graphics.getHeight()/4+noodle.getHeight()-((Gdx.graphics.getHeight()/4+noodle.getHeight())-(Gdx.graphics.getHeight()/4-needle.getHeight()*1.5f))*(needle.getX()-backgrounds[0][0].getX())/(backgrounds[0][0].getWidth()/2));
				
				if(petIndex>0)
					if(pet.getX()+pet.getWidth()*0.70f<screenViewport.getCamera().position.x-Gdx.graphics.getWidth()/2
					   && pet.getY()==pet.getHeight()/2)
						if(pet.getX()+pet.getWidth()<backgrounds[0][levelWidth-1].getX()+backgrounds[0][levelWidth-1].getWidth())
						{
							if(petRunAnim==null)
							{	
								petRunAnim = new Animation<TextureRegion>(0.017f, petIndex==1?catRunFrames:dogRunFrames);
								petRunAnim.setPlayMode(Animation.PlayMode.LOOP);
								((AnimatedSprite)pet).animation = petRunAnim;
								if(SOUND.isChecked())assetManager.get((petIndex==1?"meow" : "bark")+".mp3", Sound.class).play();
							}
							pet.setPosition(pet.getX()+nSpeedX*1.1f, pet.getY());
						}
						else
							((AnimatedSprite)pet).animation = petIdleAnim;
					else
					{
						if(!(petRunAnim==null) && pet.getY()==pet.getHeight()/2 && nSpeed>0)
						{
							boolean belowCeiling = false;
							for(int i=0;i<levelWidth;i++)
								if(ceiling[i])
									if((pet.getX()+pet.getWidth()>obstacles[i].getX()
										&& pet.getX()+pet.getWidth()<obstacles[i].getX()+obstacles[i].getWidth())
										|| (pet.getX()<obstacles[i].getX()+obstacles[i].getWidth()
										&& pet.getX()>obstacles[i].getX())
										|| (obstacles[i].getX()>pet.getX()
										&& obstacles[i].getX()+obstacles[i].getWidth()<pet.getX()+pet.getWidth()))
									{
										belowCeiling = true;
										break;
									}
							
							if(!belowCeiling && petRunAnim.getKeyFrameIndex(((AnimatedSprite)pet).stateTime)==0
								&& pet.getX()+pet.getWidth()*0.85f<noodle.getX())
							{
								pet.setPosition(pet.getX(), pet.getHeight()/2+1);
								pet.setRotation(75);
								if(SOUND.isChecked())assetManager.get("jump.wav", Sound.class).play();
								final int jumpAngle = petIndex==1? 60 : 30;
								petBounds = new Polygon(petsBounds[petIndex-1]);
								petBounds.setOrigin(pet.getWidth()/2, pet.getHeight()/2);
								if(petIndex==1)
									petBounds.setScale(-1f, 1f);
								Timer.schedule(new Timer.Task(){
									int frame = 1;
										@Override
										public void run()
										{
											if(petRunAnim.getKeyFrameIndex(((AnimatedSprite)pet).stateTime)>=30)
												((AnimatedSprite)pet).pause();
											pet.setRotation(jumpAngle-2*jumpAngle*frame/90f);
											petBounds.setRotation(pet.getRotation());
											pet.setPosition(pet.getX(), pet.getHeight()/2+(Gdx.graphics.getHeight()*0.75f-pet.getHeight()*1.5f)*(float)MathUtils.sin(MathUtils.PI*frame/90));
											if(pet.getX()+pet.getWidth()<backgrounds[0][levelWidth-1].getX()+backgrounds[0][levelWidth-1].getWidth())
												pet.setPosition(pet.getX()+nSpeedX*(petIndex==1? 1.1f : 1.25f), pet.getY());
											petBounds.setPosition(pet.getX(), pet.getY());
											if(Intersector.overlapConvexPolygons(nBounds, petBounds) && !isGameOver)
											{
												nSpeed = 0;
												if(SOUND.isChecked())
													assetManager.get("bite.mp3", Sound.class).play();
												isGameOver = true;
												showGameOver();
											}
											frame++;
											if(frame>=88)
											{
												cancel();
												((AnimatedSprite)pet).resume();
												pet.setRotation(0);
												pet.setPosition(pet.getX(), pet.getHeight()/2);
												if(isGameOver)
													((AnimatedSprite)pet).animation = petIdleAnim;
											}
										}
								}, delta, delta, 90);
							}else
								pet.setPosition(pet.getX()+nSpeedX*(pet.getX()+pet.getWidth()*0.85f<noodle.getX()? 0.95f : 0.5f), pet.getY());
						}
					}
				
				if(obstacleIndex[iHuman]>=12)
				{
					int patchX = (int)obstacles[iHuman].getX()+(int)(patchesBounds[obstacleIndex[iHuman]-12][0]*(float)obstacles[iHuman].getWidth());
					
					if(patchX-needle.getX()<Gdx.graphics.getWidth()
						&& patchX-needle.getX()>0)
					{
						int patchY = (int)(patchesBounds[obstacleIndex[iHuman]-12][1]*(float)obstacles[iHuman].getHeight());
						needle.setPosition(needle.getX(), obstacles[iHuman].getY()+patchY-(obstacles[iHuman].getY()+patchY-(Gdx.graphics.getHeight()/4-needle.getHeight()*1.5f))*(patchX-needle.getX())/Gdx.graphics.getWidth());
					}else
					if(patchX-needle.getX()>-Gdx.graphics.getWidth()
						&& patchX-needle.getX()<0)
					{
						int patchY = (int)(patchesBounds[obstacleIndex[iHuman]-12][1]*(float)obstacles[iHuman].getHeight());
						needle.setPosition(needle.getX(), obstacles[iHuman].getY()+patchY+(obstacles[iHuman].getY()+patchY-(Gdx.graphics.getHeight()/4-needle.getHeight()*1.5f))*(patchX-needle.getX())/Gdx.graphics.getWidth());
					}
				}
				neBounds.setPosition(needle.getX(), needle.getY());
				flame.setPosition(needle.getX()-flame.getWidth(), needle.getY()+needle.getHeight()*0.05f);
				
				dScore = (int)(((float)noodle.getX()/(float)backgrounds[0][0].getWidth())*10);
			}
		}

		private void showGameOver()
		{
			if(SOUND.isChecked())
			{
				planeSound.stop();
				planeSoundIntense.stop();
				rocketSound.stop();
			}
			title.setText((paused? "    PAUSED" : "GAME OVER!")+"\n");
			if(!(petRunAnim==null) && pet.getY()==pet.getHeight()/2)((AnimatedSprite)pet).animation = petIdleAnim;
			
			SCORE.setText("Score : "+String.valueOf((int)(score+dScore))+"\n\n");
			HSCORE.setText(/*HScore>0?"Server Highest : "+String.valueOf(serverHighest)+"\n" : */"");
			if((int)(score+dScore)>HScore)
			{
				HScore = (int)(score+dScore);
				updateScore(username, HScore);
			}
			
			gameOverWindow.setPosition(screenViewport.getCamera().position.x-windowNoTitleSize/2, windowNoTitleSize*25/100/2);
			title.setPosition(gameOverWindow.getX()+windowNoTitleSize/2-title.getWidth()/2, gameOverWindow.getY()+windowNoTitleSize-windowNoTitlePadding-title.getHeight());
			restartDown.setPosition(gameOverWindow.getX()+windowNoTitlePadding, gameOverWindow.getY()+windowNoTitlePadding);
			restart.setPosition(restartDown.getX(), restartDown.getY());
			homeDown.setPosition(gameOverWindow.getX()+windowNoTitleSize-buttonImageWidth-windowNoTitlePadding, restartDown.getY());
			home.setPosition(homeDown.getX(), homeDown.getY());
			resumeDown.setPosition((restartDown.getX()+homeDown.getX())/2, restartDown.getY());
			resume.setPosition(resumeDown.getX(), resumeDown.getY());
			HSCORE.setPosition(gameOverWindow.getX()+windowNoTitleSize/2-HSCORE.getWidth()/2, gameOverWindow.getY()+windowNoTitlePadding+restartDown.getHeight());
			SCORE.setPosition(gameOverWindow.getX()+windowNoTitleSize/2-SCORE.getWidth()/2, gameOverWindow.getY()+windowNoTitlePadding+restartDown.getHeight()+HSCORE.getHeight());

			gameOver.addActor(gameOverWindow);
			gameOver.addActor(title);
			gameOver.addActor(SCORE);
			gameOver.addActor(HSCORE);
			gameOver.addActor(restartDown);
			gameOver.addActor(restart);
			if(paused)
			{
				gameOver.addActor(resumeDown);
				gameOver.addActor(resume);
			}
			gameOver.addActor(homeDown);
			gameOver.addActor(home);

			pause.remove();
			pauseDown.remove();
		}
		
		public void show (){
			Gdx.input.setInputProcessor(gameOver);
		
			isGameOver = false;
			score = 0;
			
			if(SOUND.isChecked())
			{
				planeSoundId = planeSound.loop();
				rocketSoundId = rocketSound.loop();
				planeSoundIntenseId = planeSoundIntense.loop();
			}
		}

		/** @see ApplicationListener#resize(int, int) */
		public void resize (int width, int height){}

		/** @see ApplicationListener#pause() */
		public void pause (){}

		/** @see ApplicationListener#resume() */
		public void resume (){}

		/** Called when this screen is no longer the current screen for a {@link Game}. */
		public void hide (){}

		public void dispose(){}
		
		private void updateScore(String username, int HScore)
		{
			final int HSCore = HScore;
			final String userName = username;
			/*new Thread() {
				public void run() {
					FTPClient ftpClient = new FTPClient();
					try {
			 
						ftpClient.connect(server, port);
						ftpClient.login(user, pass);
						//ftpClient.enterLocalPassiveMode();
						ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
						
						String secondRemoteFile = "/public_html/"+userName;
						OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
						//System.out.println(ftpClient.getReplyCode());
						writeToStream(outputStream, new String[]{String.valueOf(HSCore)}, "SEPARATOR_NEW_LINE");
						outputStream.close();
						if(HSCore>serverHighest)
						{
							ftpClient.completePendingCommand();
							String firstRemoteFile = "/public_html/highest";
							OutputStream outputStream1 = ftpClient.storeFileStream(firstRemoteFile);
							//System.out.println(ftpClient.getReplyCode());
							writeToStream(outputStream1, new String[]{userName}, "SEPARATOR_NEW_LINE");
							outputStream1.close();
						}
					} catch (IOException ex) {
					} finally {
						try {
							if (ftpClient.isConnected()) {
								ftpClient.logout();
								ftpClient.disconnect();
							}
						} catch (IOException ex){}
					}
				}
			}.start();*/
		}

		/*private boolean writeToStream(OutputStream outputStream, String[] data, String SEPARATOR) throws IOException
		{
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

			for(String dt : data)
			{
				bufferedWriter.write(dt);
				if(SEPARATOR.matches("SEPARATOR_NEW_LINE"))
					bufferedWriter.newLine();
				else 
					bufferedWriter.write(SEPARATOR);
			}

			bufferedWriter.close();

			return true;
		}*/
	}
		
		private String readFromStream(InputStream is) throws IOException
		{
					BufferedReader br = new BufferedReader(new InputStreamReader(is));

						String data = "";
						String line;
						if ((line = br.readLine()) != null)
							data+=line+"\n";
						br.close();
			return data;
		}
	
	private class TextButtonDown extends TextButton{
		
		TextButtonStyle textButtonStyle;
		int deltaFrames = 3;
		float downToUpRatio = 9f/10f;
		
		public TextButtonDown(String text, TextButton.TextButtonStyle style){
			super(text, style);
			this.textButtonStyle = style;
			addListener(new InputListener(){
				
				@Override
				public boolean touchDown (InputEvent event, float x, float y, 
										  int pointer, int button) {
					if(SOUND.isChecked())assetManager.get("click.wav", Sound.class).play(0.5f);
					return true;
				}
				
				@Override
				public void touchDragged (InputEvent event, float x, float y, int pointer)
				{
					if(getWidth()>buttonTextWidth*downToUpRatio)
					{
						int delta = (int)(getWidth()-buttonTextWidth*downToUpRatio)/deltaFrames;
						setWidth(getWidth()-delta);
						setPosition(getX()+delta/2, getY());
					}
					if(getHeight()>buttonTextHeight*downToUpRatio)
					{
						int delta = (int)(getHeight()-buttonTextHeight*downToUpRatio)/deltaFrames;
						setHeight(getHeight()-delta);
						setPosition(getX(), getY()+delta/2);
					}
				}
				
				@Override
				public void touchUp (InputEvent event, float x, float y, 
									 int pointer, int button) {
					int deltaX = (int)(buttonTextWidth-getWidth())/2;
					int deltaY = (int)(buttonTextHeight-getHeight())/2;
					setPosition(getX()-deltaX, getY()-deltaY);
					setSize(buttonTextWidth, buttonTextHeight);
				}
			});
		}
	}
	
	private class ImageButtonDown extends Button{
		
		int deltaFrames = 3;
		float downToUpRatio = 9f/10f;
		float aspectRatio;
		
		public ImageButtonDown(String fileName){
						
			super();
			aspectRatio = (float)assetManager.get(fileName, Texture.class).getWidth()/(float)assetManager.get(fileName, Texture.class).getHeight();
						Button.ButtonStyle imageButtonStyle = new Button.ButtonStyle();
						imageButtonStyle.up = new TextureRegionDrawable(assetManager.get(fileName, Texture.class));
						imageButtonStyle.down = new TextureRegionDrawable(assetManager.get(fileName.replace(".", "_down."), Texture.class));
						imageButtonStyle.over = imageButtonStyle.down;
			setStyle(imageButtonStyle);
			addListener(new InputListener(){
				
				@Override
				public boolean touchDown (InputEvent event, float x, float y, 
										  int pointer, int button) {
					if(SOUND.isChecked())assetManager.get("click.wav", Sound.class).play(0.5f);
					return true;
				}
				
				@Override
				public void touchDragged (InputEvent event, float x, float y, int pointer)
				{
					if(getWidth()>buttonImageWidth*downToUpRatio)
					{
						int deltaX = (int)(getWidth()-buttonImageWidth*downToUpRatio)/deltaFrames;
						int deltaY = (int)(getHeight()-buttonImageWidth/aspectRatio*downToUpRatio)/deltaFrames;
						setSize(getWidth()-deltaX, getHeight()-deltaY);
						setPosition(getX()+deltaX/2, getY()+deltaY/2);
					}
				}
				
				@Override
				public void touchUp (InputEvent event, float x, float y, 
									 int pointer, int button) {
					int deltaX = (int)(buttonImageWidth-getWidth())/2;
					int deltaY = (int)(buttonImageWidth/aspectRatio-getHeight())/2;
					setPosition(getX()-deltaX, getY()-deltaY);
					setSize(buttonImageWidth, buttonImageWidth/aspectRatio);
				}
			});
		}
	}
	
	private class AnimatedImage extends Image
	{
		public Animation animation = null;
		private float stateTime = 0;

		public AnimatedImage(Animation animation) {
			super((TextureRegion)animation.getKeyFrame(0));
			this.animation = animation;
		}

		@Override
		public void act(float delta)
		{
			((TextureRegionDrawable)getDrawable()).setRegion((TextureRegion)animation.getKeyFrame(stateTime+=delta));
			super.act(delta);
		}
	}
	
	private class AnimatedSprite extends Sprite
	{
		public Animation animation = null;
		public float stateTime = 0;
		public boolean paused = false;

		public AnimatedSprite(Animation animation) {
			super((TextureRegion)animation.getKeyFrame(0));
			this.animation = animation;
		}

		@Override
		public void draw(Batch batch)
		{
			if(!paused)
				setRegion((TextureRegion)animation.getKeyFrame(stateTime+=Gdx.graphics.getDeltaTime()));
			super.draw(batch);
		}
		
		public void pause()
		{
			paused = true;
		}
		
		public void resume()
		{
			paused = false;
		}
	}
}
