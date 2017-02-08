package com.ahaproject.playdeffence.JavaUsuful.GLMatrix;

/**
 * Created by akihiro on 2017/01/30.
 * This class is matrix for opengles.
 * This class can do the following.
 * Matrix　Caluculation
 */

public class GLESMatrix {

    //this class is important value
    float[] mat = new float[16];

    //this class first
    GLESMatrix()
    {
        //The Init  is 3D matrix
        mat[0] = 1;
        mat[1] = 0;
        mat[2] = 0;
        mat[3] = 0;

        mat[4] = 0;
        mat[5] = 1;
        mat[6] = 0;
        mat[7] = 0;

        mat[8] = 0;
        mat[9] = 0;
        mat[10] = 1;
        mat[11] = 0;

        mat[12] = 0;
        mat[13] = 0;
        mat[14] = 0;
        mat[15]  =1;
    }

    //function set matrix
    GLESMatrix(GLESMatrix set_mat)
    {
        for(int i = 0; i<16;i++)
            mat[i] = set_mat.GetMatrixPart(i);
    }




    //return mat part
    public float GetMatrixPart(int no)
    {
        return mat[no];
    }

}
