package com.noodle;

import java.lang.Thread;
import java.lang.Runnable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainGame extends Game {
	
	int buttonTextWidth;
	int buttonTextHeight;
	int buttonImageWidth;
	int windowNoTitleSize;
	TextButton.TextButtonStyle textButtonStyle;
	ScreenViewport screenViewport;
	int imageToButtonPercent = 75;
	Screen mainMenuScreen;
	Screen LoadingScreen;
	AssetManager assetManager;
	BitmapFont daySansVeryLarge;
	BitmapFont daySansLarge;
	BitmapFont daySansMedium;
	int skinIndex = 0;
	int skinSelected = skinIndex;
	
	@Override
	public void create () {
		screenViewport = new ScreenViewport();
			
			assetManager = new AssetManager();
			assetManager.load("daysans_very_large.fnt", BitmapFont.class);
			assetManager.load("daysans_large.fnt", BitmapFont.class);
			assetManager.load("daysans_medium.fnt", BitmapFont.class);
			assetManager.load("button_text.png", Texture.class);
			assetManager.load("button_text_down.png", Texture.class);
			assetManager.load("click.wav", Sound.class);
			assetManager.load("close.png", Texture.class);
			assetManager.load("close_down.png", Texture.class);
			assetManager.load("window_no_title.png", Texture.class);
			assetManager.load("black_transparent_screen.png", Texture.class);
			
		/*new Thread(){
			
			public void run()
			{
				Gdx.app.postRunnable(new Runnable() {
				 @Override
				 public void run() {
					setScreen(mainMenuScreen);
				}
			  });
			}
		}.start();*/
	}
	
	public Animation<TextureRegion> getAnimationfromTexture(Texture walkSheet, int FRAME_ROWS, int FRAME_COLS, float speed, Animation.PlayMode playMode)
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
		Animation<TextureRegion> animation = new Animation<TextureRegion>(speed, walkFrames);
		animation.setPlayMode(playMode);
		return animation;
	}
 
	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		super.render();
		if(LoadingScreen==null && assetManager.update())
		{
			buttonTextHeight = Gdx.graphics.getHeight()*75/100/4;
			buttonTextWidth = buttonTextHeight*assetManager.get("button_text.png", Texture.class).getWidth()/assetManager.get("button_text.png", Texture.class).getHeight();
			buttonImageWidth = buttonTextHeight;
			windowNoTitleSize = Gdx.graphics.getHeight()*75/100;
			
			daySansMedium = assetManager.get("daysans_medium.fnt", BitmapFont.class);
			daySansLarge = assetManager.get("daysans_large.fnt", BitmapFont.class);
			daySansVeryLarge = assetManager.get("daysans_very_large.fnt", BitmapFont.class);
			daySansMedium.getData().setScale((float)Gdx.graphics.getHeight()/1080f);
			daySansLarge.getData().setScale((float)Gdx.graphics.getHeight()/1080f);
			daySansVeryLarge.getData().setScale((float)Gdx.graphics.getHeight()/1080f);
			
			LoadingScreen = new Loading(this);			
			setScreen(LoadingScreen);
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
					assetManager.load("logo_text.png", Texture.class);
					assetManager.load("settings.png", Texture.class);
					assetManager.load("settings_down.png", Texture.class);
					assetManager.load("noodle.bmp", Texture.class);
					assetManager.load("noodle_down.bmp", Texture.class);
					assetManager.load("skin0.bmp", Texture.class);
					assetManager.load("skin0_down.bmp", Texture.class);
					assetManager.load("skin0_selected.bmp", Texture.class);
					assetManager.load("beach.jpg", Texture.class);
		}

		public void render(float delta)
		{
			loading.act(Gdx.graphics.getDeltaTime());
			loading.draw();
					if(textButtonStyle==null && assetManager.update())
					{
						textButtonStyle = new TextButton.TextButtonStyle();
						textButtonStyle.font = daySansLarge;
						textButtonStyle.up = new TextureRegionDrawable(assetManager.get("button_text.png", Texture.class));
						textButtonStyle.down = new TextureRegionDrawable(assetManager.get("button_text_down.png", Texture.class));
						textButtonStyle.over = new TextureRegionDrawable(assetManager.get("button_text_down.png", Texture.class));
						
						mainMenuScreen = new MainMenu(game);
						game.setScreen(mainMenuScreen);
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
		Animation<TextureRegion> beachAnim;
		float characterDownSpeed = 0.25f;
		ImageButtonDown characterDown;
		Image skin0;
		Stage currentStage;
		
		public MainMenu(Game game){
			this.game = game;	
			mainMenu = new Stage(screenViewport);
			customization = new Stage(new ScreenViewport());
						
					AnimatedImage beach = new AnimatedImage(getAnimationfromTexture(assetManager.get("beach.jpg", Texture.class), 11, 3, 0.1f, Animation.PlayMode.LOOP_PINGPONG));
					Image NOODLE = new Image(new TextureRegionDrawable(assetManager.get("logo_text.png", Texture.class)));
					TextButtonDown continueGame = new TextButtonDown("CONTINUE", textButtonStyle);
					TextButtonDown newGame = new TextButtonDown("NEW GAME", textButtonStyle);
					ImageButtonDown settingsDown = new ImageButtonDown("settings.png");
					characterDown = new ImageButtonDown("noodle.bmp");
					characterDown.setTransform(true);
					skin0 = new Image(assetManager.get("skin0.bmp", Texture.class));
					//skin0.setTransform(true);
					skin0.setTouchable(Touchable.disabled);
					final ImageButtonDown closeDown = new ImageButtonDown("close.png");
					
					int NOODLEHeight = Gdx.graphics.getHeight()/2;
					
					if(Gdx.graphics.getWidth()>Gdx.graphics.getHeight()*16/9)
						beach.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()*9/16);
					else
						beach.setSize(Gdx.graphics.getHeight()*16/9, Gdx.graphics.getHeight());
					NOODLE.setSize(NOODLEHeight*assetManager.get("logo_text.png", Texture.class).getWidth()/assetManager.get("logo_text.png", Texture.class).getHeight(), NOODLEHeight);
					continueGame.setSize(buttonTextWidth, buttonTextHeight);
					newGame.setSize(buttonTextWidth, buttonTextHeight);
					settingsDown.setSize(buttonImageWidth, buttonImageWidth);
					characterDown.setSize(buttonImageWidth, buttonImageWidth*assetManager.get("noodle.bmp", Texture.class).getHeight()/assetManager.get("noodle.bmp", Texture.class).getWidth());
					characterDown.setOrigin(characterDown.getWidth()/2, characterDown.getHeight()/2);
					skin0.setSize(characterDown.getWidth(), characterDown.getHeight());
					skin0.setOrigin(characterDown.getWidth()/2, characterDown.getHeight()/2);
					closeDown.setSize(buttonImageWidth, buttonImageWidth);
					
					mainMenu.addActor(beach);
					mainMenu.addActor(NOODLE);
					NOODLE.setPosition(Gdx.graphics.getWidth()/2-NOODLE.getWidth()/2, Gdx.graphics.getHeight()-NOODLEHeight);
					mainMenu.addActor(continueGame);
					continueGame.setPosition(Gdx.graphics.getWidth()/2-buttonTextWidth/2, Gdx.graphics.getHeight()-NOODLEHeight-buttonTextHeight);
					mainMenu.addActor(newGame);
					newGame.setPosition(Gdx.graphics.getWidth()/2-buttonTextWidth/2, Gdx.graphics.getHeight()-NOODLEHeight-buttonTextHeight*2);
					mainMenu.addActor(settingsDown);
					settingsDown.setPosition(Gdx.graphics.getWidth()-buttonImageWidth, 0);
					mainMenu.addActor(characterDown);
					characterDown.setPosition(characterDown.getWidth()/20, characterDown.getHeight()/4);
					mainMenu.addActor(skin0);
					skin0.setPosition(characterDown.getX(), characterDown.getY());
					
					Image blackTransparentScreen = new Image(assetManager.get("black_transparent_screen.png", Texture.class));
					Image characterWindow = new Image(assetManager.get("window_no_title.png", Texture.class));
					TextButtonDown equipDown = new TextButtonDown("EQUIP", textButtonStyle);
					Label.LabelStyle SKINStyle = new Label.LabelStyle();
					SKINStyle.font = daySansLarge;
					Label SKIN = new Label("SELECT SKIN", SKINStyle);
					SKIN.setAlignment(Align.center);
					Table table = new Table();
					ScrollPane scrollPane = new ScrollPane(table);
					final Image[] skins = new Image[5];
					for(int i=0;i<5;i++)
					{
						final int iFinal = i;
						skins[i] = new Image(assetManager.get("skin0.bmp", Texture.class));
						skins[i].addListener(new ClickListener(){
							@Override
							public void clicked(InputEvent event, float x, float y) {
								for(int j=0;j<5;j++)
									skins[j].setDrawable(new TextureRegionDrawable(assetManager.get("skin0.bmp", Texture.class)));
								skinSelected = iFinal;
								skins[skinSelected].setDrawable(new TextureRegionDrawable(assetManager.get("skin0_selected.bmp", Texture.class)));
							}
						});
					}
					
					blackTransparentScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
					characterWindow.setSize(windowNoTitleSize, windowNoTitleSize);
					equipDown.setSize(buttonTextWidth, buttonTextHeight);
					SKIN.setSize(buttonTextWidth, buttonTextHeight);
					scrollPane.setSize(windowNoTitleSize*90/100, windowNoTitleSize-SKIN.getHeight()-equipDown.getHeight());
					for(int i=0;i<5;i++)
					{
						skins[i].setSize(scrollPane.getHeight()*assetManager.get("skin0.bmp", Texture.class).getWidth()/assetManager.get("skin0.bmp", Texture.class).getHeight(), scrollPane.getHeight());
						table.add(skins[i]);
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
					customization.addActor(closeDown);
					closeDown.setPosition(characterWindow.getX()+windowNoTitleSize-buttonImageWidth/2, characterWindow.getY()+windowNoTitleSize-buttonImageWidth*75/100);
					
					characterDown.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y) {
							currentStage = customization;
							skins[skinIndex].setDrawable(new TextureRegionDrawable(assetManager.get("skin0_selected.bmp", Texture.class)));
							Gdx.input.setInputProcessor(customization);
						}
					});
					
					equipDown.addListener(new ClickListener(){
						@Override
						public void clicked(InputEvent event, float x, float y) {
							skinIndex = skinSelected;
							
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
							if(currentStage==customization)
							{
								for(int j=0;j<5;j++)
									skins[j].setDrawable(new TextureRegionDrawable(assetManager.get("skin0.bmp", Texture.class)));
							}
							skinSelected = skinIndex;
							currentStage = null;
							Gdx.input.setInputProcessor(mainMenu);
						}
					});

					Gdx.input.setInputProcessor(mainMenu);
		}
		
		public void render(float delta){
			if(characterDown.getRotation() >= 15)
				characterDownSpeed*=-1;
			else
				if(characterDown.getRotation() <= -15)
					characterDownSpeed*=-1;
			characterDown.setRotation(characterDown.getRotation()+characterDownSpeed);
			skin0.setRotation(characterDown.getRotation()+characterDownSpeed);
			
			mainMenu.act(Gdx.graphics.getDeltaTime());
			mainMenu.draw();
			if(currentStage!=null)
			{
				currentStage.act(Gdx.graphics.getDeltaTime());
				currentStage.draw();
			}
		}
		
		public void show (){}

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
					assetManager.get("click.wav", Sound.class).play(0.5f);
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
					assetManager.get("click.wav", Sound.class).play(0.5f);
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
		protected Animation animation = null;
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
}
