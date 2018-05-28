package ru.maximen.copybook.widgets;

import android.app.Activity;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import ru.maximen.copybook.StaticVariables;
import ru.maximen.copybook.dto.Note;
import ru.maximen.copybook.fragments.AbstractUpdatebleFragment;

public class NoteManager {

    private final NoteList noteList;
    private final Activity activity;
    private String token;
    private List<AbstractUpdatebleFragment> abstractUpdatebleFragments;

    public NoteManager(NoteList noteList, Activity activity, String token, List<AbstractUpdatebleFragment> abstractUpdatebleFragments) {
        this.abstractUpdatebleFragments = abstractUpdatebleFragments;
        this.noteList = noteList;
        this.activity = activity;
        this.token = token;
        startUpdater();
    }

    private void startUpdater() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (StaticVariables.authorized) {
                    try {
                        String url = StaticVariables.ACCOUNT_URL + "notes";
                        HttpAuthentication authentication = new HttpBasicAuthentication(
                                "copybookapp", "ASDKLnsdoi324");

                        HttpHeaders requestHeaders = new HttpHeaders();

                        requestHeaders.setAuthorization(authentication);
                        requestHeaders.add("Authorization", "Bearer " + token);

                        HttpEntity httpEntity = new HttpEntity<>(requestHeaders);

                        RestTemplate restTemplate = new RestTemplate();
                        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                        try {
                            ResponseEntity<Note[]> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Note[].class);
                            if (response != null && response.getBody() != null) {
                                List<Note> notes = Arrays.asList(response.getBody());
                                noteList.setNoteList(notes);
                                AbstractUpdatebleFragment fragment = getVisibleFragment();
                                if (fragment != null) {
                                    fragment.onUpdateList();
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        Thread.sleep(10 * 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private AbstractUpdatebleFragment getVisibleFragment() {
        for (AbstractUpdatebleFragment abstractUpdatebleFragment : abstractUpdatebleFragments) {
            if (abstractUpdatebleFragment.isVisible()) {
                return abstractUpdatebleFragment;
            }
        }
        return null;
    }
}
