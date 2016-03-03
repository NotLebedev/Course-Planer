set(PROJECT_NAME "CoursePlanner")
set(GLOBAL_PACKAGE com/gmail/maximsmol/${PROJECT_NAME})
set(ENTRY_POINT ${GLOBAL_PACKAGE}/Main)

set(CMAKE_JAVA_TARGET_OUTPUT_DIR ${CMAKE_BINARY_DIR}/bin)
set(CMAKE_JAVA_INCLUDE_PATH ${CMAKE_BINARY_DIR}/lib/)

set(JAR_LIBS "${CMAKE_JAVA_INCLUDE_PATH}/JSON-java.jar")

if(${PROJECT_NAME} STREQUAL "Sample Project Name")
	message(AUTHOR_WARNING "No project name is set")
endif()

project(${PROJECT_NAME})
cmake_minimum_required(VERSION 2.8)

find_package(Java REQUIRED)
include(UseJava)

set(CMAKE_BUILD_TYPE debug)

set(CMAKE_JAVA_COMPILE_FLAGS "-Xlint:all" "-Xdiags:verbose")

if(${CMAKE_BUILD_TYPE} STREQUAL "debug")
	string(CONCAT ${CMAKE_JAVA_COMPILE_FLAGS} ${CMAKE_JAVA_COMPILE_FLAGS} " -g")
else()
	string(CONCAT ${CMAKE_JAVA_COMPILE_FLAGS} ${CMAKE_JAVA_COMPILE_FLAGS} " -g:none")
endif()

function(JOIN VALUES GLUE OUTPUT)
  string (REPLACE ";" "${GLUE}" _TMP_STR "${VALUES}")
  set (${OUTPUT} "${_TMP_STR}" PARENT_SCOPE)
endfunction()

file(GLOB_RECURSE SOURCES "${CMAKE_CURRENT_SOURCE_DIR}/src/*.java")
add_jar(${PROJECT_NAME} ${SOURCES} ${CMAKE_BINARY_DIR}/res INCLUDE_JARS ${JAR_LIBS} ENTRY_POINT ${ENTRY_POINT})

if(NOT EXISTS ${CMAKE_JAVA_TARGET_OUTPUT_DIR}/res)
	file(MAKE_DIRECTORY ${CMAKE_JAVA_TARGET_OUTPUT_DIR})
	add_custom_command(TARGET ${PROJECT_NAME} COMMAND ln -s ${CMAKE_BINARY_DIR}/res ${CMAKE_JAVA_TARGET_OUTPUT_DIR})
endif()

JOIN("${JAR_LIBS}" ":" JAR_LIBS_STR)
set(JAVA_ARG_STR -classpath "${JAR_LIBS_STR}:${CMAKE_JAVA_TARGET_OUTPUT_DIR}/${PROJECT_NAME}.jar" ${ENTRY_POINT})
add_custom_target(run java ${JAVA_ARG_STR} DEPENDS ${PROJECT_NAME})

add_custom_target(pmd pmd pmd -shortnames -f textcolor -d ${CMAKE_CURRENT_SOURCE_DIR}/src -l java -auxclasspath ${JAR_LIBS} -R ${CMAKE_BINARY_DIR}/pmdRuleset.xml)
add_custom_target(findbugs findbugs -effort:max -project ${CMAKE_BINARY_DIR}/findbugs.xml)
add_custom_target(debug jdb -sourcepath ${CMAKE_CURRENT_SOURCE_DIR}/src ${JAVA_ARG_STR} DEPENDS ${PROJECT_NAME})

get_filename_component(REALPATH_CMAKE_CURRENT_SOURCE_DIR ${CMAKE_CURRENT_SOURCE_DIR} REALPATH)
add_custom_target(cpd pmd cpd --minimum-tokens 50 --format text --files ${REALPATH_CMAKE_CURRENT_SOURCE_DIR}/src --language java)

