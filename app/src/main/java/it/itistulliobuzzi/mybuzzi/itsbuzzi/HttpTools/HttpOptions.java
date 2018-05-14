package it.itistulliobuzzi.mybuzzi.itsbuzzi.HttpTools;

/**
 * Questa classe si occupa di salvare le opzioni
 * per la classe {@link HttpConnect}
 *
 * Created by borg on 22/04/2016.
 */
public class HttpOptions {

    /** Default true */
    private boolean sendWithJson;
    /** Default true */
    private boolean showProgressBar;
    /** Default true */
    private boolean showErrorView;
    /** Default false */
    private boolean errorActivity;

    public HttpOptions() {
        sendWithJson = true;
        showProgressBar = true;
        showErrorView = true;
        errorActivity = false;
    }

    public boolean getSendWithJson() {
        return sendWithJson;
    }

    public HttpOptions sendWithJson(boolean sendWithJson) {
        this.sendWithJson = sendWithJson;
        return this;
    }

    public boolean getShowProgressBar() {
        return showProgressBar;
    }

    public HttpOptions showProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
        return this;
    }

    public boolean getShowErrorView() {
        return showErrorView;
    }

    public HttpOptions showErrorView(boolean showErrorView) {
        this.showErrorView = showErrorView;
        return this;
    }

    public boolean isErrorActivity() {
        return errorActivity;
    }

    public HttpOptions setErrorActivity(boolean errorActivity) {
        this.errorActivity = errorActivity;
        return this;
    }
}
