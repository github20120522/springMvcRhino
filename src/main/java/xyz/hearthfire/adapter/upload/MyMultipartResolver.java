package xyz.hearthfire.adapter.upload;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fz on 2015/5/31.
 */
public class MyMultipartResolver extends CommonsMultipartResolver {

    private static ThreadLocal<MyProgressListener> progressListener = new ThreadLocal<MyProgressListener>();

    @Override
    public void cleanupMultipart(MultipartHttpServletRequest request) {
        progressListener.get().setMultipartFinished();
        super.cleanupMultipart(request);
    }

    @Override
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        FileUpload fileUpload = super.newFileUpload(fileItemFactory);
        fileUpload.setProgressListener(progressListener.get());
        return fileUpload;
    }

    @Override
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        progressListener.set(new MyProgressListener(request));
        return super.resolveMultipart(request);
    }
}
