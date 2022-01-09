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
 *
 * @author Louis Schell
 */
public class ShaderProgram {

    private final ArrayList<Shader> shaders = new ArrayList<>();
    private int shaderProgramId;

    /**
     * Compiles and then links all shaders in {@link #shaders}.
     *
     * @throws InstantiationException if the shaders could not be linked.
     */
    public void compile() throws InstantiationException {
        shaderProgramId = glCreateProgram();

        for (Shader shader : shaders) {
            shader.compile();
            glAttachShader(shaderProgramId, shader.getShaderId());
        }

        glLinkProgram(shaderProgramId);

        int success = glGetProgrami(shaderProgramId, GL_LINK_STATUS);

        if (success == GL_FALSE) {
            int len = glGetProgrami(shaderProgramId, GL_INFO_LOG_LENGTH);
            throw new InstantiationException(
                    String.format("Failed to Link shaders.\n%s",
                            glGetProgramInfoLog(shaderProgramId, len))
            );
        }
    }

    /**
     * @return ID of the shader program.
     * @apiNote If the ID is 0, then the program has not been registered with
     * OpenGL yet.
     */
    public int getId() {
        return shaderProgramId;
    }

    /**
     * Adds all given shaders to the list of shaders to be linked.
     *
     * @param shaders shaders to add
     */
    public void attachShaders(Shader... shaders) {
        this.shaders.addAll(Arrays.asList(shaders));
    }

    /**
     * Enables usage of this program, in case we're using multiple programs.
     */
    public void use() {
        glUseProgram(shaderProgramId);
    }

    /**
     * Disables usage of this program.
     */
    public void detach() {
        glUseProgram(0);
    }

    /**
     * Uploads a uniform Mat4f to the program.
     *
     * @param varName name of the uniform variable
     * @param mat4    matrix to be uploaded to the shader
     */
    public void uploadMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(shaderProgramId, varName);
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
        use();
    }

    /**
     * Uploads a vector4f to the program.
     *
     * @param varName name of the uniform variable
     * @param vec4    vector to be uploaded to the shader
     */
    public void uploadVec4f(String varName, Vector4f vec4) {
        int varLocation = glGetUniformLocation(shaderProgramId, varName);
        glUniform4f(varLocation, vec4.x, vec4.y, vec4.z, vec4.w);
        use();
    }

    /**
     * Uploads a vector3f to the program.
     *
     * @param varName name of the uniform variable
     * @param vec3    vector to be uploaded to the shader
     */
    public void uploadVec3f(String varName, Vector3f vec3) {
        int varLocation = glGetUniformLocation(shaderProgramId, varName);
        glUniform3f(varLocation, vec3.x, vec3.y, vec3.z);
        use();
    }

    /**
     * Uploads a float to the program.
     *
     * @param varName name of the uniform variable
     * @param val     float to be uploaded to the shader
     */
    public void uploadFloat(String varName, float val) {
        int varLocation = glGetUniformLocation(shaderProgramId, varName);
        glUniform1f(varLocation, val);
        use();
    }

    /**
     * Uploads an int to the program.
     *
     * @param varName name of the uniform variable
     * @param val     int to be uploaded to the shader
     */
    public void uploadInt(String varName, int val) {
        int varLocation = glGetUniformLocation(shaderProgramId, varName);
        use();
        glUniform1f(varLocation, val);
    }

    /**
     * Uploads a texture to the program.
     *
     * @param varName name of the uniform variable
     * @param slot    shader slot the texture should be loaded into
     */
    public void uploadTexture(String varName, int slot) {
        uploadInt(varName, slot);
    }
}