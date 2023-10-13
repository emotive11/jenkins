pipeline {
  agent any
  stages {
    stage('Initialize') {
      steps {
        script {
          def SVN_BRANCH_NAME = "trunk"
          def SVN_BRANCH_VERSION = "7.0"
          def BUILD_VERSION = "${SVN_BRANCH_VERSION}.0.XXXXX"
          def LICENSE_KEY_DIAG_MANAGER = "D6ZKH-07C82-MJO9S-8LDTM-9UYU6"
          def SUB_JOBS = [
            UPDATE_SVN: "update_svn",
            BUILD_DM: "build_diagmanager",
            BUILD_RT7: "build_rt7",
            BUILD_OTF: "build_otf_solution",
            BUILD_RT2ND_DOTNET: "build_rt2nd_dotnet",
            BUILD_RT2ND_JAVA: "build_rt2nd_java",
            COLLECT_AND_ZIP_FILE: "collect_binary_files_for_creating_release_zip_files",
            TEST_DM: "test_diagmanager",
            TEST_API_RUNTIME_RT7: "test_api_runtime_rt7",
            UPDATE_BINARIES_DIAGAMANAGER_DOTNET_TO_RT2ND_DOTNET: "update_binaries_diagmanager_dotnet_to_rt2ndgen_dotnet_libs",
            UPDATE_NECESSARY_BINARIES_2_CPP_CUSTOM_IMPL: "update_necessary_binaries_to_cpp_tools_customimplementation",
            ZIP_SOURCE: "zip_source",
            GENERATE_DOCUMENT: "generate_doc_and_doc_api",
            GENERATE_DEBUG_SDK: "generate_debug_sdk",
            CHANGE_VERSION_SOME_FILES: "change_characters_some_files_before_build",
            COLLECT_ALL_FILES_TO_RUN_UNITTESTS: "collect_all_files_to_run_unittests",
            COLLECT_SETUPS_BUILT_FROM_OTF_SOLUTION: "collect_some_setup_file_built_from_otf_solution",
            ZIP_PTX_FILES: "zip_ptxs_file",
            TEST_EXECUTE_PUX: "test_unittest_extension",
            TEST_UNITTESTS_OTF_SOLUTION: "test_all_unittests_in_otf_solution",
            CHECK_FILES_BUILT: "check_files_built",
            EXPORT_PROJECTS_BY_RUNTIME_CONVERTER: "export_projects_by_runtime_converter",
            SEND_EMAIL: "send_mail",
            TEST_ALL: "test_all"
          ]

          def MAIL_SEND_REPORT_TESTCASES_FAILED_TO = 'ngoc.tran@emotive-software.com'

          def OS_TYPE_WINDOW = "WINDOW"
          def OS_TYPE_LINUX = "LINUX"

          def VW_MCD_HOME_WIN_32 = "C:\\Program Files (x86)\\Volkswagen\\VW-MCD-20\\MCD-Kernel"
          def VW_MCD_HOME_WIN_64 = "C:\\Program Files\\Volkswagen\\VW-MCD-20\\MCD-Kernel"
          def VW_MCD_CONFIG_WIN_32 = "C:\\Users\\Public\\Volkswagen\\VW-MCD-20\\config"
          def VW_MCD_CONFIG_WIN_64 = "C:\\Users\\Public\\Volkswagen\\VW-MCD-20-x64\\config"
          def VW_MCD_HOME_LINUX_32 = "/opt/vw/lib/"
          def VW_MCD_HOME_LINUX_64 = "/opt/vw/lib64/"

          def DIAGMANAGER_SOURCE = "D:\\OutSources\\OtxDM\\${SVN_BRANCH_NAME}"
          def OTF_BRANCH = "D:\\Products\\OpenTestSystem\\${SVN_BRANCH_NAME}"
          def OTF_SOURCE = "${OTF_BRANCH}\\src"
          def DIRECTORY_PTXS = "D:\\Test\\OtxUnitTest\\PTXs"

          def PROJECT_FOLDER = "${OTF_SOURCE}\\Rt2ndGen\\Dotnet\\OpenTestSystem.Otx.Runtime2.Api.UnitTestGenerator"

          def SVN_REVISION = "lastest"
          def BUILD_SERVER_FOLDER = "dm_rt7"
          def DIAGMANAGER_GEN_PATH_WIN_32 = "D:\\dm32"
          def DIAGMANAGER_GEN_PATH_WIN_64 = "D:\\dm64"

          def RT7_GEN_PATH_WIN_32 = "D:\\rt32"
          def RT7_GEN_PATH_WIN_64 = "D:\\rt64"

          def DIAGMANAGER_GEN_PATH_WIN_32_DEBUG = "D:\\dm32_Debug"
          def RT7_GEN_PATH_WIN_32_DEBUG = "D:\\rt32_Debug"

          def REPORT_FOLDER = "D:\\CollectFiles\\UnitTest_Report"

          // generate path on Linux

          def DIAGMANAGER_GEN_PATH_LINUX_32 = "/home/build/dm/${SVN_BRANCH_NAME}/x86_32"
          def DIAGMANAGER_GEN_PATH_LINUX_64 = "/home/build/dm/${SVN_BRANCH_NAME}/x86_64"

          def RT7_GEN_PATH_LINUX_32 = "/home/build/rt7/${SVN_BRANCH_NAME}/x86_32"
          def RT7_GEN_PATH_LINUX_64 = "/home/build/rt7/${SVN_BRANCH_NAME}/x86_64"

          def INSTALL_PATH_WIN32 = "${OTF_SOURCE}\\Libs\\Win32"
          def INSTALL_PATH_WIN64 = "${OTF_SOURCE}\\Libs\\Win64"
          def INSTALL_PATH_LINUX32 = "/mnt/d/Products/OpenTestSystem/${SVN_BRANCH_NAME}/src/Libs/Linux32"
          def INSTALL_PATH_LINUX64 = "/mnt/d/Products/OpenTestSystem/${SVN_BRANCH_NAME}/src/Libs/Linux64"
          def INSTALL_PATH_WIN32_DEBUG = "${OTF_SOURCE}\\Libs\\Win32_Debug"

          def RT7_DM_OPTIONS = "-D USE_VWMCD90=OFF -D USE_VWMCD_LINK_LIBRARY=OFF -D USE_VWMCDSP=OFF -D ADD_DL_LIBS=ON -D DIAGMANAGER_BUILD_STATIC_LIBS=OFF -D RT7_BUILD_STATIC_LIBS=OFF"

          def BUILD_CONFIGURATION_TYPE = "Release"

          def DIRECTORY_COLLECT_FILES = "D:\\CollectFiles\\UnknownVersion\\"
          def DIRECTORY_SETUP_DAILY = "D:\\SetupDaily\\UnknownVersion\\"

          def DIRECTORY_CONTAIN_ALL_PUX = "${OTF_BRANCH}\\test\\PUX Storage\\PUX_6.99\\"
          ArrayList LIST_PUX_DIAGNOSTIC_UNITTEST = ["${OTF_BRANCH}\\test\\PUX Storage\\PUX_6.99\\DiagnosticUnitTestCases\\OtxRuntimeUnitTestDiagCom.pux",
          "${OTF_BRANCH}\\test\\PUX Storage\\PUX_6.99\\DiagnosticUnitTestCases\\OtxRuntimeUnitTestDiagDataBrowsingPlus.pux",
          "${OTF_BRANCH}\\test\\PUX Storage\\PUX_6.99\\DiagnosticUnitTestCases\\OtxRuntimeUnitTestFlash.pux"]
          ArrayList LIST_PUX_NO_DIAGNOSTIC_UNITTEST = ["${OTF_BRANCH}\\test\\PUX Storage\\PUX_6.99\\OtherUnitTestCases\\OtxRuntimeUnitTestLogic.pux",
          "${OTF_BRANCH}\\test\\PUX Storage\\PUX_6.99\\OtherUnitTestCases\\OtxRuntimeUnitTestQuantities.pux", 											"${OTF_BRANCH}\\test\\PUX Storage\\PUX_6.99\\OtherUnitTestCases\\OtxRuntimeUnitTestTestResultHandling.pux"]
          def CANCEL_BUILD = false

          def LOCK_KEY_OTF_SOLUTION = "Lock build solution OTF inside  ${OTF_SOURCE}"
        }

      }
    }

    stage('SVN Checkout') {
      steps {
        script {
          def LAST_REVISION_COMMITTED_BEFORE_UPDATED = ''

          def remote = ["host": "192.168.1.77", "allowAnyHosts": true]
          withCredentials([sshUserPrivateKey(credentialsId: 'build-server-window', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName')]) {
            remote.user = userName
            remote.name = userName
            remote.identityFile = identity

            LAST_REVISION_COMMITTED_BEFORE_UPDATED = sshCommand remote: remote, command: """svn info "${OTF_BRANCH}" --show-item last-changed-revision"""
          }

          try {
            build job: SUB_JOBS.UPDATE_SVN, parameters: [
              string(name: 'SOURCE', value: DIAGMANAGER_SOURCE),
              string(name: 'REVISION', value: params.REVISION)
            ]
            build job: SUB_JOBS.UPDATE_SVN, parameters: [
              string(name: 'SOURCE', value: OTF_BRANCH),
              string(name: 'REVISION', value: params.REVISION)
            ]
          }
          catch (Exception e)
          {
            catchError { sshCommand remote: remote, command: """svn update "${OTF_BRANCH}" -r ${LAST_REVISION_COMMITTED_BEFORE_UPDATED}""" }
            catchError { sshCommand remote: remote, command: """svn update "${DIAGMANAGER_SOURCE}" -r ${LAST_REVISION_COMMITTED_BEFORE_UPDATED}""" }
            error ("Exist problem with server SVN")
          }

          catchError { build job: SUB_JOBS.UPDATE_SVN, parameters: [
            string(name: 'SOURCE', value: DIRECTORY_PTXS),
            string(name: 'REVISION', value: params.REVISION)
          ] }

          withCredentials([sshUserPrivateKey(credentialsId: 'build-server-window', keyFileVariable: 'identity', passphraseVariable: '', usernameVariable: 'userName')]) {
            remote.user = userName
            remote.name = userName
            remote.identityFile = identity

            SVN_REVISION = sshCommand remote: remote, command: """svn info ${DIAGMANAGER_SOURCE} --show-item revision"""

            BUILD_VERSION = "${SVN_BRANCH_VERSION}.0.${SVN_REVISION}"
            currentBuild.displayName = "${BUILD_VERSION}-#${currentBuild.number}"

            def LAST_REVISION_COMMITTED_AFTER_UPDATED = sshCommand remote: remote, command: """svn info "${OTF_BRANCH}" --show-item last-changed-revision"""

            if (LAST_REVISION_COMMITTED_BEFORE_UPDATED == LAST_REVISION_COMMITTED_AFTER_UPDATED && params.CHECK_HAS_NEW_COMMITTED == true) {
              unstable 'NO NEW COMMITMENT.'
              CANCEL_BUILD = true
            }

            DIRECTORY_COLLECT_FILES = "D:\\CollectFiles\\OpenTestSystem_${BUILD_VERSION}"
            DIRECTORY_SETUP_DAILY = "D:\\Setup\\OpenTestSystem_${BUILD_VERSION}"

            if(params.NO_DIGIAL_SIGNATURE == true) {
              DIRECTORY_SETUP_DAILY = "${DIRECTORY_SETUP_DAILY}_NoSig"
            } else {
              try {
                sshCommand remote: remote, command: """ "C:\\Program Files (x86)\\Windows Kits\\10\\bin\\10.0.19041.0\\x64\\signtool.exe" sign """+
                """-debug """+
                """-fd sha256 """+
                """-tr "http://timestamp.digicert.com" """+
                """-td sha256 """+
                """-d "OpenTestSystem.Otx.Runtime2.Runner" """+
                """-sha1 86498957137b0a20d8763c1c773b9bbad660cb71 """+
                """ "${OTF_SOURCE}\\Libs\\Win32\\OpenTestSystem.Otx.Runtime2.Runner.exe" """
              }
              catch (Exception e)
              {
                unstable 'EXCEPTION WHEN SETTING DIGIAL SIGNATURE.'
                CANCEL_BUILD = true
              }
            }

            REPORT_FOLDER = "${DIRECTORY_COLLECT_FILES}\\UnitTest_Report_${BUILD_VERSION}"
          }

          println "BRANCH=${SVN_BRANCH_NAME}"
          println "VERSION=${BUILD_VERSION}"
          println "REPORT_FOLDER=${REPORT_FOLDER}"
          println "-------------------------------------------------------------------"
          println "-------------------------------------------------------------------"
          println "----------------------------SUMMARY--------------------------------"
          println "DIRECTORY_COLLECT_FILES=${DIRECTORY_COLLECT_FILES}"
          println "DIRECTORY_SETUP_DAILY=${DIRECTORY_SETUP_DAILY}"
        }

      }
    }

    stage('Update file version') {
      when {
        expression {
          return CANCEL_BUILD == false
        }

      }
      steps {
        catchError() {
          build(job: SUB_JOBS.CHANGE_VERSION_SOME_FILES, parameters: [
            					string(name: 'DIRECTORY_SOURCE_OTS', value: OTF_SOURCE),
            					string(name: 'DIRECTORY_SOURCE_DIAGMANAGER', value: DIAGMANAGER_SOURCE),
            					string(name: 'VERSION', value: BUILD_VERSION),
            					string(name: 'REVISION', value: SVN_REVISION),
            					booleanParam(name: 'NO_DIGIAL_SIGNATURE', value: params.NO_DIGIAL_SIGNATURE)
            				])
          }

        }
      }

      stage('Send mail') {
        when {
          expression {
            return params.MAIL_TO.length() > 0 && CANCEL_BUILD == false
          }

        }
        steps {
          catchError() {
            build(job: SUB_JOBS.SEND_EMAIL, parameters: [
              					string(name: 'SEND_TO', value: params.MAIL_TO),
              					string(name: 'FOLDER', value: DIRECTORY_SETUP_DAILY)
              				])
            }

          }
        }

      }
      post {
        always {
          script {
            cleanWs(cleanWhenNotBuilt: false,
            deleteDirs: true,
            disableDeferredWipeout: true,
            notFailBuild: true)
          }

        }

      }
      parameters {
        string(name: 'REVISION', defaultValue: '', description: 'Process the code at revision? Latest revision if empty.')
        booleanParam(name: 'CHECK_HAS_NEW_COMMITTED', defaultValue: false, description: 'This means that if set to false, the job will move on to building the source code without checking out a new commit.')
        booleanParam(name: 'ZIP_SOURCE_AND_GENERATE_DOCUMENT', defaultValue: true, description: '')
        booleanParam(name: 'BUILD_API_WINDOWS', defaultValue: true, description: '')
        booleanParam(name: 'BUILD_API_LINUX', defaultValue: true, description: '')
        booleanParam(name: 'BUILD_API_WIN32_DEBUG', defaultValue: true, description: '')
        booleanParam(name: 'BUILD_SETUP_OTF', defaultValue: true, description: '')
        booleanParam(name: 'EXECUTE_UNITTEST_EXTENSION', defaultValue: true, description: '')
        booleanParam(name: 'EXECUTE_ALL_UNITTESTS', defaultValue: true, description: '')
        booleanParam(name: 'USE_RUNTIME_CONVERTER_TO_CONVERT_PROJECTS', defaultValue: true, description: '')
        booleanParam(name: 'NO_DIGIAL_SIGNATURE', defaultValue: false, description: 'No set digial signature by file token')
        string(name: 'MAIL_TO', defaultValue: '', description: 'Send email link google.')
      }
    }