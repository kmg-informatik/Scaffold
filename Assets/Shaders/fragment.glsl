#version 330 core

uniform sampler2D TEX_SAMPLER;

in vec3 fColor;
in vec2 fTexCoords;
in float fTexId;
out vec4 outColor;

bool texCoordsInRange();

void main() {

    vec4 color;

    if(texCoordsInRange()){
        color = texture(TEX_SAMPLER, fTexCoords);
    }else{
        color = vec4(fColor, 1.0);
    }

    if (color.a <= 0){
        discard;
    }

    outColor = color;
}

bool texCoordsInRange(){
    return (fTexCoords.x >=0 &&
    fTexCoords.x <= 1 &&
    fTexCoords.y >= 0 &&
    fTexCoords.y <= 1);
}