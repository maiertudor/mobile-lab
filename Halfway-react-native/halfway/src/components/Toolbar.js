import React from 'react';
import { ToolbarAndroid, View, ListView, StyleSheet, Text, TouchableOpacity, StatusBar } from 'react-native';

export default class JobList extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {

        return (
            <View style={styles.toolbar}>
                <View style={styles.toolbarContent}>
                    <Text style={styles.toolbarText}>HalfWay</Text>
                </View>

            </View>
        );
    }
}
const styles = StyleSheet.create({
    toolbar: {
        height: 70,
        backgroundColor: "#01579B",
        flex: 0,
        flexDirection: "row",
        alignItems: "flex-end"
    },
    toolbarContent: {
        flex: 1,
        alignItems: "center"
    },
    toolbarText: {
        color: "#fff",
        fontSize: 20,
        margin: 10,
        fontWeight: "bold"
    }
});