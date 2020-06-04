package com.mygdx.game.entities.npc.dialog;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.stage.SmartStage;
import lombok.Setter;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DialogLine {
    private final String DIALOG_STYLE = "white";
    private final String TEXT_STYLE = "LoraButtonBlack";
    private final String BUTTON_STYLE = "LoraButton";

    private final int ALWAYS_AVAILABLE = -1;
    private List<Speech> speechList = new ArrayList<>();
    private Skin skin;
    @Setter
    private SmartStage stage;
    private int currentIndex = 0;

    @SneakyThrows
    public DialogLine(FileHandle fileHandle, Skin skin){
        this.skin = skin;
        parseJson(fileHandle.readString());
    }

    public void runDialog(String name){
        Speech speech = speechList.get(currentIndex);
        Dialog dialog = new Dialog(name, skin, DIALOG_STYLE) {
            public void result(Object obj) {
                currentIndex = (Integer)obj;
                System.out.println(currentIndex);
                if(currentIndex>=0){
                    runDialog(name);
                }else {
                    currentIndex = 0;
                }
            }
        };
        Label text = new Label(speech.text,skin,TEXT_STYLE);
        dialog.text(text);
        for(Answer answer:speech.answers){
            if(answer.phase!=ALWAYS_AVAILABLE && answer.phase!=stage.getCurrentQuestIndex()){
                continue;
            }
            ImageTextButton btn = new ImageTextButton(answer.text, skin, BUTTON_STYLE);
            dialog.button(btn,answer.target);
        }
        dialog.show(stage);
    }

    @SneakyThrows
    private void parseJson(String string){
        JSONArray unparsedSpeeches = new JSONArray(string);
        for(int i = 0; i < unparsedSpeeches.length(); i++){
            speechList.add(new Speech(unparsedSpeeches.getJSONObject(i)));
        }
    }

    private class Speech{
        private String text;
        private List<Answer> answers = new ArrayList<>();
        @SneakyThrows
        public Speech(JSONObject jsonObject){
            text = jsonObject.getString("text");
            JSONArray unparsedAnswers = jsonObject.getJSONArray("answers");
            for(int i = 0; i < unparsedAnswers.length(); i++){
                answers.add(new Answer(unparsedAnswers.getJSONObject(i)));
            }
        }

    }

    private class Answer{
        private String text;
        private int target;
        private int phase = ALWAYS_AVAILABLE;
        @SneakyThrows
        public Answer(JSONObject jsonObject){
            text = jsonObject.getString("text");
            target = jsonObject.getInt("target");
            if(jsonObject.has("phase")){
                phase = jsonObject.getInt("phase");
            }
        }
    }
}
