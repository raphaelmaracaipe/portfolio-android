#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_externals_ApiKeysExternal_getApiKeyDev(JNIEnv *env, jobject thiz) {
    std::string hello = "V!QFCVVd09KWbRd@a7TZF-A1W8d!aSZ5?2@B2!#f";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_externals_ApiKeysExternal_getApiKeyProd(JNIEnv *env, jobject thiz) {
    std::string hello = "";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_externals_KeysExternal_getSeed(JNIEnv *env, jobject thiz) {
    std::string hello = "RF22SW76BV83EDH8";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_externals_KeysExternal_getKey(JNIEnv *env, jobject thiz) {
    std::string hello = "askjd4432ajdl@q9";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_externals_SpKeysExternal_getTokenKey(JNIEnv *env, jobject thiz) {
    std::string hello = "tokenKeySp";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_externals_SpKeysExternal_getTokenEditAccessAndRefreshKey(JNIEnv *env, jobject thiz) {
    std::string hello = "tokenKeyAccessAndRefreshSp";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_externals_SpKeysExternal_getDeviceKey(JNIEnv *env, jobject thiz) {
    std::string hello = "deviceIdKey";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_externals_SpKeysExternal_getDeviceEditKey(JNIEnv *env, jobject thiz) {
    std::string hello = "deviceIdKeyEdit";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_externals_SpKeysExternal_getKeySp(JNIEnv *env, jobject thiz) {
    std::string hello = "keys";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_br_com_raphaelmaracaipe_core_externals_SpKeysExternal_getKeySpEdit(JNIEnv *env, jobject thiz) {
    std::string hello = "keysEdit";
    return env->NewStringUTF(hello.c_str());
}