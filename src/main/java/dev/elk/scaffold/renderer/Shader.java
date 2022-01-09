package dev.elk.scaffold.renderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20.*;

/**
 * Shader objects wrap the shader source code, the original
 * filepath, the shader type, and the shader ID needed in
 * and OpenGL shader program. <p>
 * Shader code can be compiled, by calling {@link #compile()} and
 * is then linked by the {@link ShaderProgram}.
 * @see ShaderProgram
 * @author Louis Schell
 */
public class Shader {

    private final String filepath;
    private final ShaderType shaderType;
    private final String shaderSource;
    private int shaderId;

    public Shader(String filepath, ShaderType shaderType) throws IOException {
        this.filepath = filepath;
        this.shaderType = shaderType;
        this.shaderSource = loadShaderSource(filepath);
    }

    public void compile() throws InstantiationException {
        shaderId = glCreateShader(shaderType.GL_TYPE);

        glShaderSource(shaderId, shaderSource);
        glCompileShader(shaderId);

        int success = glGetShaderi(shaderId, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(shaderId, GL_INFO_LOG_LENGTH);
            throw new InstantiationException(
                    String.format("Failed to compile %s-Shader %s\n%s",
                            shaderType,
                            filepath,
                            glGetShaderInfoLog(shaderId, len)
                    )
            );
        }
    }

    private static String loadShaderSource(String filepath) throws IOException {
        return new String(Files.readAllBytes(Path.of(filepath)));
    }

    public ShaderType getShaderType() {
        return shaderType;
    }

    public String getFilepath() {
        return filepath;
    }

    public int getShaderId() {
        return shaderId;
    }

    public enum ShaderType{
        VERTEX(GL_VERTEX_SHADER),
        FRAGMENT(GL_FRAGMENT_SHADER);

        final int GL_TYPE;

        ShaderType(int GL_TYPE){
            this.GL_TYPE = GL_TYPE;
        }
    }

}