package com.balu.kmlespressotest;

import android.content.Intent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.web.webdriver.DriverAtoms;
import androidx.test.espresso.web.webdriver.Locator;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static androidx.test.espresso.web.sugar.Web.onWebView;
import static androidx.test.espresso.web.webdriver.DriverAtoms.clearElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.findElement;
import static androidx.test.espresso.web.webdriver.DriverAtoms.getText;
import static androidx.test.espresso.web.webdriver.DriverAtoms.webClick;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(AndroidJUnit4.class)
public class WebViewActivityTest {

    private static final String MACCHIATO = "Macchiato";
    private static final String DOPPIO = "Doppio";

    @Rule
    public ActivityTestRule<WebViewActivity> activityTestRule =
            new ActivityTestRule<WebViewActivity>(WebViewActivity.class,false,false){
                @Override
                protected void afterActivityLaunched() {
                  //  super.afterActivityLaunched();
                    onWebView().forceJavascriptEnabled();
                }
            };

    @Test
    public void typeTextInInput_clickButton_SubmitsForm() {
        // Lazily launch the Activity with a custom start Intent per test
        activityTestRule.launchActivity(withWebFormIntent());

        // Selects the WebView in your layout. If you have multiple WebViews you can also use a
        // matcher to select a given WebView, onWebView(withId(R.id.web_view)).

        onWebView()
        // Find the input element by ID
        .withElement(findElement(Locator.ID, "text_input"))
        // Clear previous input
        .perform(clearElement())
        // Enter text into the input element
        .perform(DriverAtoms.webKeys(DOPPIO))
                // Find the change text button.
                .withElement(findElement(Locator.ID, "changeTextBtn"))
        // Click on it.
        .perform(webClick())
                // Find the message element by ID
                .withElement(findElement(Locator.ID, "message"))
        // Verify that the text is displayed
        .check(webMatches(getText(),containsString(DOPPIO)));

    }
    private static Intent withWebFormIntent() {
        Intent basicFormIntent = new Intent();
        basicFormIntent.putExtra(WebViewActivity.KEY_URL_TO_LOAD, WebViewActivity.WEB_FORM_URL);
        return basicFormIntent;
    }
}
