package com.soltrix.stargame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("background.jpg");

		// сложение векторов
		Vector2 v1 = new Vector2(1,3);
		Vector2 v2 = new Vector2(0, -1);
		v1.add(v2);
		System.out.println("v1.x = " + v1.x + " v1.y = " + v1.y);

		// вычитание векторов
		v1.set(4,3);
		v2.set(1,2);
		v1.sub(v2);
		System.out.println("v1.x = " + v1.x + " v1.y = " + v1.y);

		// скалирование векторов (умножение на кожффициент)
		v1.set(10,20);
		v1.scl(0.9f);
		System.out.println("v1.x = " + v1.x + " v1.y = " + v1.y);

		// вычисление длины вектора (теорема Пифагора)
		System.out.println("v1.len = " + v1.len());

		// единичный вектор направления (нормализачия)
		v1.nor();
		System.out.println("v1.x = " + v1.x + " v1.y = " + v1.y);
		System.out.println("v1.len = " + v1.len());

		// скалярное произведение векторов
		//v2.dot(v1);
		System.out.println(v2.dot(v1));
		System.out.println("v2.x = " + v2.x + " v2.y = " + v2.y);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
