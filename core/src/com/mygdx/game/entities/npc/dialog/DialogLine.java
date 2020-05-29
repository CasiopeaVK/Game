package com.mygdx.game.entities.npc.dialog;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import lombok.Setter;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DialogLine {
    private List<Speech> speechList = new ArrayList<>();
    private Skin skin;
    @Setter
    private Stage stage;
    private int currentIndex = 0;

    @SneakyThrows
    public DialogLine(FileHandle fileHandle, Skin skin){
        this.skin = skin;
        parseJson(fileHandle.readString());
    }

    public void runDialog(String name){
        Speech speech = speechList.get(currentIndex);
        Dialog dialog = new Dialog(name, skin, "default") {
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
        dialog.text(speech.text);
        for(Answer answer:speech.answers){
            dialog.button(answer.text,answer.target);
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
        @SneakyThrows
        public Answer(JSONObject jsonObject){
            text = jsonObject.getString("text");
            target = jsonObject.getInt("target");
        }
    }
}