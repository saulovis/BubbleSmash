package com.mitnickgame.bubblesmash;

import java.util.Date;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.sound.SoundEngine;

import com.mitnickgame.bubblesmash.classes.Config;
import com.mitnickgame.bubblesmash.controller.ConfigController;
import com.mitnickgame.bubblesmash.game.scenes.TitleScene;
import com.mitnickgame.bubblesmash.R;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import static com.mitnickgame.bubblesmash.config.DeviceSettings.jaCriouEditText;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.telaInicial;

public class MainActivity extends Activity {
	private CCScene scene;
	//private InterstitialAd interstitial;
	private ConfigController cc;
	public static Config config;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		jaCriouEditText = false;
		//procurando configurações a serem respeitadas
		cc = new ConfigController(this);
		config = cc.buscarPorId(1l, true);
		if(config == null){
			//sem configuração cadastrada então deve gravar as padrão
			config = new Config();
			config.setPlayMusic(true);
			config.setPlayEffects(true);
			config.setDataUltimoAcesso(new Date());
			cc.cadastrar(config);
		}else{
			config.setDataUltimoAcesso(new Date());
			cc.alterar(config, Config._ID+"=?",new String []{config.getId().toString()});
		}
		//cria o anúncio intersticial.
		//interstitial = new InterstitialAd(this);
		//interstitial.setAdUnitId("ca-app-pub-5289464342605005/2540641175");
		
		// portrait orientation
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// view
		CCGLSurfaceView glSurfaceView = new CCGLSurfaceView(this);
		setContentView(glSurfaceView);
		CCDirector.sharedDirector().attachInView(glSurfaceView);
		
		// configure CCDirector 
		CCDirector.sharedDirector().setScreenSize(480, 720);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onStart(){
		super.onStart();
		//criando AdView na UIThread
		CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
			public void run() {
				LinearLayout.LayoutParams adParams = new LinearLayout.LayoutParams(
		                getWindowManager().getDefaultDisplay().getWidth(),                        
		                getWindowManager().getDefaultDisplay().getHeight()+getWindowManager().getDefaultDisplay().getHeight()-100);
				
				/*final AdView ad = new AdView(CCDirector.sharedDirector().getActivity());
				ad.setId(5000);
				ad.setAdSize(AdSize.BANNER);
				ad.setAdUnitId("ca-app-pub-5289464342605005/1063907974");
		        AdRequest request = new AdRequest.Builder().build();
		        ad.loadAd(request);
		        CCDirector.sharedDirector().getActivity().addContentView(ad, adParams);*/
		    }
		});
		//chamando a primeira scene
		scene = new TitleScene().scene();
		CCDirector.sharedDirector().runWithScene(scene);
	}
	
	@Override
	public void onBackPressed() {
		if(config.getPlayEffects())
			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.burst);
		
		if (telaInicial) {
			finish();
		} else {
			CCDirector.sharedDirector().replaceScene(new TitleScene().scene());
		}
		// deixar em branco para desabilitar qualquer acao do botao
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        /*if(!interstitial.isLoaded()) {
    	    AdRequest adRequest = new AdRequest.Builder().build();
    	    interstitial.loadAd(adRequest);
        }*/
    }
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		SoundEngine.sharedEngine().realesAllSounds();
		/*if (interstitial.isLoaded()) {
        	interstitial.show();
        }*/
	}
}
