package dev.elk.scaffold.renderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

/**
 * The ShaderProgram links shaders with each other to create a
 * shader program. It gets added in OpenGL and can then be used.
 * OpenGL needs the shaderProgramID for many function calls.<p>
 * If a shader fails to compile or the program cannot be linked,
 * the OpenGL error is printed to the error stream.
 * @author Louis Schell
 */
public class ShaderProgram {

    private final ArrayList<Shader> shaders = new ArrayList<>();
    private int shaderProgramId;

    public void compile(){
        shaderProgramId = glCreateProgram();

        for (Shader shader : shaders) {
            shader.compile();
            glAttachShader(shaderProgramId, shader.getShaderId());
        }

        glLinkProgram(shaderProgramId);

        int success = glGetProgrami(shaderProgramId, GL_LINK_STATUS);

        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderProgramId, GL_INFO_LOG_LENGTH);
            System.err.println("ERROR:\tLinking of shaders failed.");
            System.err.println(glGetProgramInfoLog(shaderProgramId, len));
        }
    }

    public int getId() {
        return shaderProgramId;
    }

    public void attachShaders(Shader...shaders){
        this.shaders.addAll(Arrays.asList(shaders));
    }

    public void use(){
        glUseProgram(shaderProgramId);
    }

    public void detach() {
        glUseProgram(0);
    }

    public void uploadMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(shaderProgramId, varName);
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
        use();
    }

    public void uploadVec4f(String varName, Vector4f vec4) {
        int varLocation = glGetUniformLocation(shaderProgramId, varName);
        glUniform4f(varLocation, vec4.x,vec4.y,vec4.z,vec4.w);
        use();
    }

    public void uploadVec3f(String varName, Vector3f vec) {
        int varLocation = glGetUniformLocation(shaderProgramId, varName);
        glUniform3f(varLocation, vec.x,vec.y,vec.z);
        use();
    }

    public void uploadFloat(String varName, float val) {
        int varLocation = glGetUniformLocation(shaderProgramId, varName);
        glUniform1f(varLocation,val);
        use();
    }

}
