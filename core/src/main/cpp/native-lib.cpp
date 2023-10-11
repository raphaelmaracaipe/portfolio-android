#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_network_utils_ApiKeysExternal_getApiKeyDev(JNIEnv *env, jobject thiz) {
    std::string hello = "d2e621a6646a4211768cd68e26f21228a81";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_network_utils_ApiKeysExternal_getApiKeyProd(JNIEnv *env, jobject thiz) {
    std::string hello = "ca03na188ame03u1d78620de67282882a84";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_network_utils_KeysExternal_getSeed(JNIEnv *env, jobject thiz) {
    std::string hello = "RF22SW76BV83EDH8";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_network_utils_KeysExternal_getKey(JNIEnv *env, jobject thiz) {
    std::string hello = "askjd4432ajdl@q9";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_network_utils_SpKeysExternal_getTokenKey(JNIEnv *env, jobject thiz) {
    std::string hello = "tokenKeySp";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_network_utils_SpKeysExternal_getTokenEditAccessAndRefreshKey(JNIEnv *env,
                                                                                               jobject thiz) {
    std::string hello = "tokenKeyAccessAndRefreshSp";
    return env->NewStringUTF(hello.c_str());
}