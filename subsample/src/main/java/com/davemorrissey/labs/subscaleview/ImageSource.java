package com.davemorrissey.labs.subscaleview;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Helper class used to set the source and additional attributes from a variety of sources. Supports
 * use of a bitmap, asset, resource, external file or any other URI.
 *
 * When you are using a preview image, you must set the dimensions of the full size image on the
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class ImageSource {

    static final String FILE_SCHEME = "file:///";
    static final String ASSET_SCHEME = "file:///android_asset/";

    private final Uri uri;
    private boolean tile;
    private int sWidth;
    private int sHeight;
    private boolean cached;
    private int orientation;
    private String mimeType;

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    private ImageSource(Uri uri) {
        // #114 If file doesn't exist, attempt to url decode the URI and try again
        String uriString = uri.toString();
        if (uriString.startsWith(FILE_SCHEME)) {
            File uriFile = new File(uriString.substring(FILE_SCHEME.length() - 1));
            if (!uriFile.exists()) {
                try {
                    uri = Uri.parse(URLDecoder.decode(uriString, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    // Fallback to encoded URI. This exception is not expected.
                }
            }
        }
        this.uri = uri;
        this.tile = true;
    }

    private ImageSource(int resource) {
        this.uri = null;
        this.tile = true;
    }

    /**
     * Create an instance from a resource. The correct resource for the device screen resolution will be used.
     * @param resId resource ID.
     * @return an {@link ImageSource} instance.
     */
    public static ImageSource resource(int resId) {
        return new ImageSource(resId);
    }

    /**
     * Create an instance from an asset name.
     * @param assetName asset name.
     * @return an {@link ImageSource} instance.
     */
    public static ImageSource asset(String assetName) {
        if (assetName == null) {
            throw new NullPointerException("Asset name must not be null");
        }
        return uri(ASSET_SCHEME + assetName);
    }

    /**
     * Create an instance from a URI. If the URI does not start with a scheme, it's assumed to be the URI
     * of a file.
     * @param uri image URI.
     * @return an {@link ImageSource} instance.
     */
    public static ImageSource uri(String uri) {
        if (uri == null) {
            throw new NullPointerException("Uri must not be null");
        }
        if (!uri.contains("://")) {
            if (uri.startsWith("/")) {
                uri = uri.substring(1);
            }
            uri = FILE_SCHEME + uri;
        }
        return new ImageSource(Uri.parse(uri));
    }

    /**
     * Create an instance from a URI.
     * @param uri image URI.
     * @return an {@link ImageSource} instance.
     */
    public static ImageSource uri(Uri uri) {
        if (uri == null) {
            throw new NullPointerException("Uri must not be null");
        }
        return new ImageSource(uri);
    }

    public static ImageSource uri(Uri uri, int width, int height, int orientation, String mimeType) {
        if (uri == null) {
            throw new NullPointerException("Uri must not be null");
        }
        ImageSource imageSource = new ImageSource(uri);
        imageSource.mimeType = mimeType;
        imageSource.sWidth = width;
        imageSource.sHeight = height;
        imageSource.orientation = orientation;
        return imageSource;
    }

    public boolean supportScale () {
        return mimeType.startsWith("image/") && !mimeType.endsWith("gif")
                && !mimeType.endsWith("mpo") && !mimeType.endsWith("bmp");
    }

    /**
     * Enable tiling of the image. This does not apply to preview images which are always loaded as a single bitmap.,
     * and tiling cannot be disabled when displaying a region of the source image.
     * @return this instance for chaining.
     */
    public ImageSource tilingEnabled() {
        return tiling(true);
    }

    /**
     * Disable tiling of the image. This does not apply to preview images which are always loaded as a single bitmap,
     * and tiling cannot be disabled when displaying a region of the source image.
     * @return this instance for chaining.
     */
    public ImageSource tilingDisabled() {
        return tiling(false);
    }

    /**
     * Enable or disable tiling of the image. This does not apply to preview images which are always loaded as a single bitmap,
     * and tiling cannot be disabled when displaying a region of the source image.
     * @param tile whether tiling should be enabled.
     * @return this instance for chaining.
     */
    public ImageSource tiling(boolean tile) {
        this.tile = tile;
        return this;
    }

    public final Uri getUri() {
        return uri;
    }


    protected final boolean getTile() {
        return tile;
    }

    protected final int getSWidth() {
        if (orientation % 180 ==0) {
            return sWidth;
        } else {
            return sHeight;
        }
    }

    protected final int getSHeight() {
        if (orientation % 180 ==0) {
            return sHeight;
        } else {
            return sWidth;
        }
    }

    protected final boolean isCached() {
        return cached;
    }
}
