package com.wangzs.app_base.base.android

import android.Manifest
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import androidx.annotation.Nullable
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import com.wangzs.app_base.R
import com.wangzs.app_base.module_base.utils.EasyPermissionsEx
import com.wangzs.app_base.module_base.utils.log.LogUtil

open class PermissionActivity : RxAppCompatActivity() {
    var RC_SETTINGS_SCREEN = 0x1001
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating) {
            val result = fixOrientation()
            LogUtil.d("onCreate fixOrientation when Oreo, result = $result")
        }
        super.onCreate(savedInstanceState)
    }

    private fun fixOrientation(): Boolean {
        try {
            val field =
                Activity::class.java.getDeclaredField("mActivityInfo")
            field.isAccessible = true
            val o = field[this] as ActivityInfo
            o.screenOrientation = -1
            field.isAccessible = false
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private val isTranslucentOrFloating: Boolean
        private get() {
            var isTranslucentOrFloating = false
            try {
                val styleableRes =
                    Class.forName("com.android.internal.R\$styleable")
                        .getField("Window")[null] as IntArray
                val ta = obtainStyledAttributes(styleableRes)
                val m = ActivityInfo::class.java.getMethod(
                    "isTranslucentOrFloating",
                    TypedArray::class.java
                )
                m.isAccessible = true
                isTranslucentOrFloating = m.invoke(null, ta) as Boolean
                m.isAccessible = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return isTranslucentOrFloating
        }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating) {
            LogUtil.d("avoid calling setRequestedOrientation when Oreo.")
            return
        }
        super.setRequestedOrientation(requestedOrientation)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                LogUtil.d("onRequestPermissionsResult: Permission granted")
            } else {
                LogUtil.d("onRequestPermissionsResult: Permission denied")
                if (EasyPermissionsEx.somePermissionPermanentlyDenied(
                        this,
                        needPermissionsReadExternalStorage
                    )
                ) {
                    EasyPermissionsEx.goSettings2Permissions(
                        this,
                        getString(R.string.str_permission_setting_save),
                        getString(R.string.str_setting),
                        RC_SETTINGS_SCREEN
                    )
                }
            }
            PERMISSIONS_REQUEST_VOICE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                LogUtil.d("onRequestPermissionsResult: Permission granted")
            } else {
                LogUtil.d("onRequestPermissionsResult: Permission denied")
                if (EasyPermissionsEx.somePermissionPermanentlyDenied(
                        this,
                        *needPermissionsVoice
                    )
                ) {
                    EasyPermissionsEx.goSettings2Permissions(
                        this,
                        getString(R.string.str_permission_setting_sound),
                        getString(R.string.str_setting),
                        RC_SETTINGS_SCREEN
                    )
                }
            }
            PERMISSIONS_REQUEST_VOICE_EXTERNAL -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                LogUtil.d("onRequestPermissionsResult: Permission granted")
            } else {
                LogUtil.d("onRequestPermissionsResult: Permission denied")
                if (EasyPermissionsEx.somePermissionPermanentlyDenied(
                        this,
                        *needPermissionsVoiceExternal
                    )
                ) {
                    EasyPermissionsEx.goSettings2Permissions(
                        this,
                        getString(R.string.str_permission_setting_save_sound),
                        getString(R.string.str_setting),
                        RC_SETTINGS_SCREEN
                    )
                }
            }
            PERMISSIONS_REQUEST_CAMERA -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                LogUtil.d("onRequestPermissionsResult: Permission granted")
            } else {
                LogUtil.d("onRequestPermissionsResult: Permission denied")
                if (EasyPermissionsEx.somePermissionPermanentlyDenied(
                        this,
                        *needPermissionsCamera
                    )
                ) {
                    EasyPermissionsEx.goSettings2Permissions(
                        this,
                        getString(R.string.str_permission_setting_camera),
                        getString(R.string.str_setting),
                        RC_SETTINGS_SCREEN
                    )
                }
            }
            PERMISSIONS_REQUEST_CAMERA_EXTERNAL -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED
            ) {
                LogUtil.d("onRequestPermissionsResult: Permission granted")
            } else {
                LogUtil.d("onRequestPermissionsResult: Permission denied")
                if (EasyPermissionsEx.somePermissionPermanentlyDenied(
                        this,
                        *needPermissionsCameraExternal
                    )
                ) {
                    EasyPermissionsEx.goSettings2Permissions(
                        this,
                        getString(R.string.str_permission_setting_save_camera_micphone),
                        getString(R.string.str_setting),
                        RC_SETTINGS_SCREEN
                    )
                }
            }
            PERMISSIONS_REQUEST_LOCATION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                LogUtil.d("onRequestPermissionsResult: Permission granted")
            } else {
                LogUtil.d("onRequestPermissionsResult: Permission denied")
                if (EasyPermissionsEx.somePermissionPermanentlyDenied(
                        this,
                        *needPermissionsLocations
                    )
                ) {
                    EasyPermissionsEx.goSettings2Permissions(
                        this,
                        getString(R.string.str_permission_setting_local),
                        getString(R.string.str_setting),
                        RC_SETTINGS_SCREEN
                    )
                }
            }
            PERMISSIONS_REQUEST_INIT -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED
            ) {
                LogUtil.d("onRequestPermissionsResult: Permission granted")
            } else {
                LogUtil.d("onRequestPermissionsResult: Permission denied")
                if (EasyPermissionsEx.somePermissionPermanentlyDenied(
                        this,
                        *needPermissionsInit
                    )
                ) {
                    EasyPermissionsEx.goSettings2Permissions(
                        this,
                        getString(R.string.str_permission_setting_certain_save_camera_micphone),
                        getString(R.string.str_setting),
                        RC_SETTINGS_SCREEN
                    )
                }
            }
            PERMISSIONS_REQUEST_READ_CONTACTS -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                LogUtil.d("onRequestPermissionsResult: Permission granted")
            } else {
                LogUtil.d("onRequestPermissionsResult: Permission denied")
                if (EasyPermissionsEx.somePermissionPermanentlyDenied(
                        this,
                        needPermissionsReadContacts
                    )
                ) {
                    EasyPermissionsEx.goSettings2Permissions(
                        this,
                        goSettingsRationaleContacts,
                        getString(R.string.str_setting),
                        RC_SETTINGS_SCREEN
                    )
                }
            }
            PERMISSIONS_REQUEST_READ_PHONE_STATE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                LogUtil.d("onRequestPermissionsResult: Permission granted")

            } else {
                LogUtil.d("onRequestPermissionsResult: Permission denied")
                if (EasyPermissionsEx.somePermissionPermanentlyDenied(
                        this,
                        needPermissionsReadContacts
                    )
                ) {
                    EasyPermissionsEx.goSettings2Permissions(
                        this,
                        getString(R.string.str_permission_setting_contact),
                        getString(R.string.str_setting),
                        RC_SETTINGS_SCREEN
                    )
                }
            }
            PERMISSIONS_REQUEST_JOIN -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED
            ) {
                LogUtil.d("onRequestPermissionsResult: Permission granted")
            } else {
                LogUtil.d("onRequestPermissionsResult: Permission denied")
                if (EasyPermissionsEx.somePermissionPermanentlyDenied(
                        this,
                        *needPermissionsJoin
                    )
                ) {
                    EasyPermissionsEx.goSettings2Permissions(
                        this,
                        getString(R.string.str_permission_setting_certain_save_camera_micphone),
                        getString(R.string.str_setting),
                        RC_SETTINGS_SCREEN
                    )
                }
            }
            PERMISSIONS_REQUEST_SENDSMS -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                LogUtil.d("onRequestPermissionsResult: Permission granted")
            } else {
                LogUtil.d("onRequestPermissionsResult: Permission denied")
                if (EasyPermissionsEx.somePermissionPermanentlyDenied(
                        this,
                        *needPermissionsSendSMS
                    )
                ) {
                    EasyPermissionsEx.goSettings2Permissions(
                        this,
                        getString(R.string.str_permission_setting_sms),
                        getString(R.string.str_setting),
                        RC_SETTINGS_SCREEN
                    )
                }
            }
            PERMISSIONS_REQUEST_CALLPHONE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                LogUtil.d("onRequestPermissionsResult: Permission granted")
            } else {
                LogUtil.d("onRequestPermissionsResult: Permission denied")
                if (EasyPermissionsEx.somePermissionPermanentlyDenied(
                        this,
                        needPermissionsCallPhone
                    )
                ) {
                    EasyPermissionsEx.goSettings2Permissions(
                        this,
                        getString(R.string.str_permission_mobile),
                        getString(R.string.str_setting),
                        RC_SETTINGS_SCREEN
                    )
                }
            }
            PERMISSIONS_REQUEST_MUST -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                LogUtil.d("onRequestPermissionsResult: Permission granted")

            } else {
                LogUtil.d("onRequestPermissionsResult: Permission denied")
                if (EasyPermissionsEx.somePermissionPermanentlyDenied(
                        this, *needPermissionsMust
                    )
                ) {
                    EasyPermissionsEx.goSettings2Permissions(
                        this,
                        getString(R.string.str_permission_setting_save_mobile),
                        getString(R.string.str_setting),
                        RC_SETTINGS_SCREEN
                    )
                }
            }
            else -> LogUtil.d("onRequestPermissionsResult: default error...")
        }
    }

    companion object {
        const val rationaleCameraExternal = "需要访问存储设置、相机、麦克风的权限"
        const val rationale = "需要访问存储设置、相机、麦克风的权限"
        const val rationaleContacts = "需要读取通讯录的权限"
        const val rationaleJoin = "需要访问存储设置、相机、麦克风的权限"
        const val rationaleLocation = "需要访问定位的权限"
        const val rationaleSDCard = "需要访问存储的权限"
        const val rationalePhoneState = "需要访问电话权限"
        const val rationaleVoiceExternal = "需要访问录音和存储的权限"
        const val rationaleVoice = "需要访问麦克风的权限"
        const val rationaleCamera = "需要访问摄像头权限"
        const val rationaleSMS = "需要发送短信的权限"
        const val goSettingsRationaleSDCard = "需要访问存储设备的权限，但此权限已被禁止，你可以到设置中更改"
        const val goSettingsRationaleCamera = "需要访问存摄像头权限，但此权限已被禁止，你可以到设置中更改"
        const val goSettingsRationaleSendSms = "需要访问发送短信，但此权限已被禁止，你可以到设置中更改"
        const val goSettingsRationaleVoice = "需要访问麦克风录音，但此权限或被禁止，你可以到设置中更改"
        const val goSettingsRationaleCameraExternal =
            "需要访问存储设备、相机、麦克风的权限，但此权限已被禁止，你可以到设置中更改"
        const val goSettingsRationaleVoiceExternal = "需要访问录音和存储的权限，但两者权限或被禁止，你可以到设置中更改"
        private const val goSettingsRationaleInit = "需要访问存储设备、相机、麦克风，但某项权限已被禁止，你可以到设置中更改"
        private const val goSettingsRationaleJoin =
            "需要访问存储设备、相机、麦克风的权限，但某项权限已被禁止，你可以到设置中更改"
        private const val goSettingsRationaleLocation = "需要定位的权限,但此权限已被禁止，你可以到设置中更改"
        const val goSettingsRationaleContacts = "需要读取通讯录的权限,但此权限已被禁止，你可以到设置中更改"
        const val goSettingsRationaleReadPhoneState = "需要读取通讯录的权限,但此权限已被禁止，你可以到设置中更改"
        const val goSettingsRationaleMust = "为保证app正常使用,需要访问存储设备和电话的权限,但此权限已被禁止，你可以到设置中更改"
        const val PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0x10
        const val PERMISSIONS_REQUEST_CAMERA = 0x11
        const val PERMISSIONS_REQUEST_VOICE = 0x12
        const val PERMISSIONS_REQUEST_CAMERA_EXTERNAL = 0x13
        const val PERMISSIONS_REQUEST_VOICE_EXTERNAL = 0x14
        const val PERMISSIONS_REQUEST_LOCATION = 0x15
        const val PERMISSIONS_REQUEST_INIT = 0x16
        const val PERMISSIONS_REQUEST_READ_CONTACTS = 0x17
        const val PERMISSIONS_REQUEST_READ_PHONE_STATE = 0x18
        const val PERMISSIONS_REQUEST_JOIN = 0x19
        const val PERMISSIONS_REQUEST_SENDSMS = 0x20
        const val PERMISSIONS_REQUEST_CALLPHONE = 0x21
        const val PERMISSIONS_REQUEST_MUST = 0x22
        val needPermissionExternal = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val needPermissionsVoice =
            arrayOf(Manifest.permission.RECORD_AUDIO)
        val needPermissionsCamera =
            arrayOf(Manifest.permission.CAMERA)
        val needPermissionsSendSMS =
            arrayOf(Manifest.permission.SEND_SMS)
        val needPermissionsVoiceExternal = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val needPermissionsCameraExternal = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        )
        const val needPermissionsReadExternalStorage =
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        const val needPermissionsReadContacts = Manifest.permission.READ_CONTACTS
        const val needPermissionsCallPhone = Manifest.permission.CALL_PHONE
        val needPermissionsReadPhoneState =
            arrayOf(Manifest.permission.READ_PHONE_STATE)
        val needPermissionsInit = arrayOf(
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE
        )
        val needPermissionsJoin = arrayOf(
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE
        )
        val needPermissionsLocations = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val needPermissionsMust =
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}