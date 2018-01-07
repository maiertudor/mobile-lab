import React from 'react';
import { ToolbarAndroid, View, ListView, StyleSheet, Text, TouchableOpacity, StatusBar, Button } from 'react-native';

export default class ListItem extends React.Component {
    static navigationOptions = {
        title: 'Welcome',
    };
    constructor(props) {
        super(props);
    }
    render() {
        return (
            <TouchableOpacity>
                <View style={styles.container}>
                    <Text style={styles.textLeft}>{this.props.rowData.name}</Text>
                    <Text style={styles.textLeft}>{this.props.rowData.description}</Text>
                    <Text style={styles.textRight}>{this.props.rowData.date}</Text>
                    <Text style={styles.textRight}>{this.props.rowData.employer}</Text>
                </View >
            </TouchableOpacity>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        padding: 10,
        margin: 5,
        backgroundColor: '#ECEFF1',
        borderRadius: 10,
        borderWidth: 1,
        borderColor: "#E0E0E0"
    },
    textLeft: {
        color: '#263238',
        textAlign: "left",
        fontSize: 18
    },
    textRight: {
        color: '#263238',
        textAlign: "right"
    }
});