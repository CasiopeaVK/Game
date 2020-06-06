package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.mygdx.game.Constants;
import com.mygdx.game.utils.IsoUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class HackingArcade extends Entity {
    private static final String PROGRESS_BAR_STYLE = "default-horizontal";
    private final int RADIUS = 100;// dim*x=2r x=2r/dim
    private final int RANDOM_VECTOR_GENERATION_INTERVAL = 5*1000;
    private final float EASINESS = 1f;

    private Sprite key;
    private ShapeRenderer shapeRenderer;
    private Vector2 lastRandomVector = generateRandomVector();
    private long lastRandomVectorGenerationTime = System.currentTimeMillis();
    private boolean isLastKeyMoveByMouse = false;
    private ProgressBar progressBar;
    private int progress = 0;
    private Consumer<Boolean> onEnd;

    public HackingArcade(Sprite key,Consumer<Boolean> onEnd) {
        this.onEnd = onEnd;

        this.key = key;
        formatKey();

        progressBar =  new ProgressBar(0, 1000, 1, false, Constants.APP_SKIN, PROGRESS_BAR_STYLE);
        progressBar.setWidth(Gdx.graphics.getWidth());

        shapeRenderer = new ShapeRenderer();
    }

    private void formatKey() {
        formatKeySize();
        calculateKeyPosition();
    }

    private void formatKeySize() {
        float dimension = key.getHeight() < key.getWidth() ? key.getWidth() : key.getHeight();
        float scale = RADIUS / dimension;
        key.setScale(scale);
        calculateKeyPosition();
    }

    private void calculateKeyPosition() {
        key.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    }

    @Override
    public void update() {
        processKeyMovement();

        Circle circle = new Circle(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, RADIUS);

        Color color =  Color.RED;
        if(circleRectangleIntersection(circle, key.getBoundingRectangle())){
            color = Color.BLACK;
            progress+=1;
        }else{
            progress-=1/EASINESS;
        }
        progressBar.setValue(progress);
        Batch batch = getStage().getBatch();
        batch.begin();
        key.draw(batch);
        progressBar.draw(batch,1);
        batch.end();

        shapeRenderer.setColor(color);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(circle.x, circle.y, circle.radius);
        Rectangle r = key.getBoundingRectangle();
        shapeRenderer.rect(r.x, r.y, r.width, r.height);
        shapeRenderer.end();

        if(progress>=progressBar.getMaxValue()){
            remove();
            onEnd.accept(true);
        }
        if(progress<=progressBar.getMinValue()){
            remove();
            onEnd.accept(false);
        }
    }

    private boolean circleRectangleIntersection(Circle c, Rectangle r) {
        return Intersector.overlaps(c, r);
    }

    private Vector2 generateRandomVector() {
        int x = ThreadLocalRandom.current().nextInt(-100, 100 + 1);
        int y = ThreadLocalRandom.current().nextInt(-100, 100 + 1);
        return IsoUtils.getDirection(new Vector2(x, y));
    }

    private Vector2 timingGenerateRandomVector(){
        if(System.currentTimeMillis()>lastRandomVectorGenerationTime+RANDOM_VECTOR_GENERATION_INTERVAL){
            lastRandomVector = generateRandomVector();
            lastRandomVectorGenerationTime = System.currentTimeMillis();
        }
        return lastRandomVector;
    }

    private void processKeyMovement() {
        Vector2 vec;
        if(isLastKeyMoveByMouse){
            vec = timingGenerateRandomVector();
            isLastKeyMoveByMouse = false;
        }else{
            isLastKeyMoveByMouse = true;
            vec = IsoUtils.getDirection(new Vector2(Gdx.input.getX() - Gdx.graphics.getWidth() / 2, Gdx.input.getY() - Gdx.graphics.getHeight() / 2));
            vec = IsoUtils.multiplyVector(vec,EASINESS);
        }
        float x = key.getX() + vec.x;
        float y = key.getY() - vec.y;
        key.setPosition(x, y);
    }
}
