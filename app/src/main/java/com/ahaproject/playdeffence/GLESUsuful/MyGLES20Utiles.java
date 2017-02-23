package com.ahaproject.playdeffence.GLESUsuful;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLException;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by akihiro on 2017/02/22.
 *  @author 杉澤 浩二
 */

public class MyGLES20Utiles {

    /**
     * ログ出力用のタグです。
     */
    private static String TAG = "GLES20Utils";

    /**
     * オブジェクトが無効であることを表します。<p>
     *
     * @see {@link #createProgram(String, String)}
     * @see {@link #loadShader(int, String)}
     */
    public static final int INVALID = 0;

    /**
     * インスタンス化できない事を強制します。
     */
    private MyGLES20Utiles() {}

    /**
     * 最初の要素の位置です。
     */
    private static final int FIRST_INDEX = 0;

    private static final int DEFAULT_OFFSET = 0;

    private static final int FLOAT_SIZE_BYTES = 4;

    /**
     * 指定されたプリミティブ型配列のデータを { FloatBuffer} へ変換して返します。
     *
     * @param array バッファデータ
     * @return 変換されたバッファデータ
     * @see { GLES20#glVertexAttribPointer(int, int, int, boolean, int, java.nio.Buffer)}
     */
    public static FloatBuffer createBuffer(float[] array) {
        final FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        buffer.put(array).position(FIRST_INDEX);
        return buffer;
    }

    /**
     * 指定されたバーテックスシェーダとフラグメントシェーダを使用してプログラムを生成します。
     *
     * @param vertexSource ポリゴン描画用バーテックスシェーダのソースコード
     * @param fragmentSource 色描画用のフラグメントシェーダのソースコード
     * @return プログラムハンドラまたは {@link #INVALID}
     *  GLException OpenGL API の操作に失敗した場合
     */
    public static int createProgram(final String vertexSource, final String fragmentSource) throws GLException {
        // バーテックスシェーダをコンパイルします。
        final int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == INVALID) {
            return INVALID;
        }

        // フラグメントシェーダをコンパイルします。
        final int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == INVALID) {
            return INVALID;
        }

        // プログラムを生成して、プログラムへバーテックスシェーダとフラグメントシェーダを関連付けます。
        int program = GLES20.glCreateProgram();
        if (program != INVALID) {
            // プログラムへバーテックスシェーダを関連付けます。
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            // プログラムへフラグメントシェーダを関連付けます。
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");

            GLES20.glLinkProgram(program);
            final int[] linkStatus = new int[1];
            //リンクが成功したかを　第3引数の箇所に入れる。
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, DEFAULT_OFFSET);
            if (linkStatus[FIRST_INDEX] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                //シェーダーコンパイル時のログを見る。
                Log.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = INVALID;
            }
        }

        return program;
    }

    /**
     * 指定されたシェーダのソースコードをコンパイルします。
     *
     * @param shaderType シェーダの種類
     * @param source シェーダのソースコード
     * @return シェーダハンドラまたは {@link #INVALID}
     * @see { GLES20#GL_VERTEX_SHADER}
     * @see { GLES20.GL_FRAGMENT_SHADER}
     */
    public static int loadShader(final int shaderType, final String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != INVALID) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            final int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, DEFAULT_OFFSET);
            if (compiled[FIRST_INDEX] == INVALID) {
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = INVALID;
            }
        }
        return shader;
    }

    /**
     * 指定された直前の OpenGL API 操作についてエラーが発生しているかどうか検証します。
     *
     * @param op 検証する直前に操作した OpenGL API 名
     * @throws GLException 直前の OpenGL API 操作でエラーが発生している場合
     */
    public static void checkGlError(final String op) throws GLException {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new GLException(error, op + ": glError " + error);
        }
    }

    /**
     * 指定された {Bitmap} 情報をテクスチャへ紐付けます。
     *
     * @param bitmap テクスチャへ紐付ける {Bitmap} 情報
     * @return テクスチャ ID
     */
    public static int loadTexture(final Bitmap bitmap) {
        return loadTexture(bitmap, GLES20.GL_NEAREST, GLES20.GL_LINEAR);
    }

    /**
     * 指定された { Bitmap} 情報をテクスチャへ紐付けます。<p>
     * この実装は簡易なテクスチャの初期化のみで繰り返しの指定をサポートしません。
     *
     *  bitmap テクスチャへ紐付ける  Bitmap} 情報
     *  min テクスチャを縮小するときの補完方法
     * mag テクスチャを拡大するときの補完方法
     * テクスチャ ID
     *  { GLES20#GL_TEXTURE_MIN_FILTER}
     * { GLES20#GL_TEXTURE_MAG_FILTER}
     */
    public static int loadTexture(final Bitmap bitmap, final int min, final int mag) {
        final int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, DEFAULT_OFFSET);

        final int texture = textures[FIRST_INDEX];
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        // テクスチャを拡大/縮小する方法を設定します。
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, min);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, mag);

        return texture;
    }

}
