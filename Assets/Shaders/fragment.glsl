#version 330 core

uniform sampler2D TEX_SAMPLER;

in vec2 fTexCoords;
out vec4 outColor;

void main() {
    outColor = texture(TEX_SAMPLER, fTexCoords);
    if(outColor.a == 0)
        discard;
}