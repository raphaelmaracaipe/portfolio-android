#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_network_utils_ApiKeys_getApiKeyDev(JNIEnv *env, jobject thiz) {
    std::string hello = "d2e621a6646a4211768cd68e26f21228a81";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_network_utils_ApiKeys_getApiKeyProd(JNIEnv *env, jobject thiz) {
    std::string hello = "ca03na188ame03u1d78620de67282882a84";
    return env->NewStringUTF(hello.c_str());
}