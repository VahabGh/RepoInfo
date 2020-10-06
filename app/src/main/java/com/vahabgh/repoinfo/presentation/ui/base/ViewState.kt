package com.vahabgh.repoinfo.presentation.ui.base

sealed class ViewState {

    class Loading : ViewState() {

        override fun equals(other: Any?): Boolean {
            return toString() == other.toString()
        }

        override fun toString(): String {
            return "Loading"
        }
    }

    class StopLoading : ViewState() {

        override fun equals(other: Any?): Boolean {
            return toString() == other.toString()
        }

        override fun toString(): String {
            return "StopLoading"
        }
    }

    class ShowError : ViewState() {

        override fun equals(other: Any?): Boolean {
            return toString() == other.toString()
        }

        override fun toString(): String {
            return "ShowError"
        }
    }

    class ShowContent : ViewState() {

        override fun equals(other: Any?): Boolean {
            return toString() == other.toString()
        }

        override fun toString(): String {
            return "ShowContent"
        }
    }

}